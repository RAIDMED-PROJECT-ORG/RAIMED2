import type { VirtualPatient } from '@/models/virtual-patient/virtualPatient.model';
import { defineStore } from 'pinia';
import axiosInstance from '@/service/httpClient/axios.config';
import type { Gender } from '@/models/virtual-patient/gender.enum';
import type { NewPatient } from '@/models/new-patient/newPatient.model';
import { TypeAction } from '@/models/virtual-patient/typeAction.enum';
import { QuestionType } from '@/models/question/questionType.enum';

import { parse } from 'js2xmlparser';
import {useAuthStore} from '@/stores/auth.store';
import type {Listen} from "@/models/listen/listen.model";
import type {Question} from "@/models/question/question.model";
import type {Prescription} from "@/models/prescription/prescription.model";

interface PatientState {
  virtualPatients: VirtualPatient[];
}

const authStore = useAuthStore()

export const usePatientStore = defineStore('patient', {
  state: (): PatientState => ({
    virtualPatients: []
  }),
  getters: {
    getVirtualPatients(): VirtualPatient[] {
      return this.virtualPatients;
    }
  },
  actions: {
    async init() {
      this.virtualPatients = [];
      this.virtualPatients = await this.fetchVirtualPatients()
    },
    async fetchVirtualPatients() {
      let patients: VirtualPatient[] = [];
      const res = await axiosInstance.get('/virtual-patient');
      if (res && res.status === 200) {
        patients = res.data.map((item: any) => this.parseVirtualPatient(item));
      }
      this.virtualPatients = patients;
      return patients;
    },
    async fetchVirtualPatient(id: string): Promise<VirtualPatient | null> {
        const res = await axiosInstance.get(`/virtual-patient/${id}`);
        if (res && res.status === 200) {
            return this.parseVirtualPatient(res.data);
        }
        return null;
    },
    async deleteVirtualPatient(id: string): Promise<boolean> {
      const res = await axiosInstance.delete(`/virtual-patient/${id}`);
      return res && res.status === 200;

    },
    async saveNewPatient(newPatient: NewPatient): Promise<boolean> {
      const virtualPatient = createVirtualPatient(newPatient);
      const virtualPatientXML = parse('VirtualPatient', virtualPatient);
      const res = await axiosInstance.post('/virtual-patient/xml', virtualPatientXML, {
        headers: {
          'Content-Type': 'application/xml'
        }
      });
      return res && res.status === 200;
    },
    parseVirtualPatient(data: any): VirtualPatient {
      return {
        id: String(data.id),
        age: data.age,
        gender: data.gender as Gender,
        createdBy: data.createdBy,
        createdAt: data.createdAt,
        actions: data.actions,
        result: data.result
      };
    }
  }
});

function createVirtualPatient(newPatient: NewPatient) {
    return {
        age: newPatient.characteristic?.age,
        gender: newPatient.characteristic?.gender,
        result: newPatient.characteristic?.diagnostic,
        createdAt: new Date().toISOString(),
        createdBy: createCreatedBy(),
        actions: {
            action: [
                ...createQuestionActions(newPatient.questions),
                ...newPatient.listen.map(createSpontaneousPatientSpeechAction),
                ...newPatient.biology.map(createBiologyPrescriptionAction),
                ...newPatient.biopsy.map(createBiopsyPrescriptionAction),
                ...newPatient.imagery.map(createImageryPrescriptionAction)
            ]
        }
    };
}

function createSpontaneousPatientSpeechAction(listen: Listen) {
    return {
        type: TypeAction.SPONTANEOUS_PATIENT_SPEECH,
        primaryElement: listen.content,
        actionSpontaneousPatientSpeech: {
            speech: listen.content,
        }
    };
}

function createBiologyPrescriptionAction(prescription: Prescription) {
    return {
        type: TypeAction.BIOLOGY_MICROBIOLOGY_PRESCRIPTION,
        primaryElement: prescription.content,
        actionPrescription: {
            prescription: prescription
        }
    };
}

function createBiopsyPrescriptionAction(prescription: Prescription) {
    return {
        type: TypeAction.BIOPSIES_PRESCRIPTION,
        primaryElement: prescription.content,
        actionPrescription: {
            prescription: prescription
        }
    };
}

function createImageryPrescriptionAction(prescription: Prescription) {
    return {
        type: TypeAction.IMAGING_PRESCRIPTION,
        primaryElement: prescription.content,
        actionPrescription: {
            prescription: prescription
        }
    };
}

function createCreatedBy() {
  return {
    id: authStore.getUserInfo.id,
    firstname: authStore.getUserInfo.username,
    lastname: authStore.getUserInfo.lastname,
    email: authStore.getUserInfo.email,
    role: authStore.getUserInfo.role,
  };
}

function createQuestionActions(questions: Question[]) {
  return questions.map((question) => ({
    type: (question.type === QuestionType.CLOSED) ? TypeAction.CLOSED_QUESTION : TypeAction.OPENED_QUESTION,
    primaryElement: question.content,
    ...(question.type === QuestionType.CLOSED ? {
      actionClosedQuestion: {
        closedAnswer: question.answer,
        questionLinked: question,
      },
    } : {
      actionOpenedQuestion: {
        openedAnswer: question.answer,
        questionLinked: question,
      },
    }),
  }));
}
