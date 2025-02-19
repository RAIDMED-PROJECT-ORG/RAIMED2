import type {VirtualPatient} from '@/models/virtual-patient/virtualPatient.model';
import {defineStore} from 'pinia';
import axiosInstance from '@/service/httpClient/axios.config';
import type {Gender} from '@/models/virtual-patient/gender.enum';
import type {NewPatient} from '@/models/new-patient/newPatient.model';
import {TypeAction} from '@/models/virtual-patient/typeAction.enum';
import {QuestionType} from '@/models/question/questionType.enum';

import {parse} from 'js2xmlparser';
import {useAuthStore} from '@/stores/auth.store';
import {HttpStatusCode} from 'axios';

interface PatientState {
  virtualPatients: VirtualPatient[];
}

const authStore = useAuthStore();

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
      this.virtualPatients = await this.fetchVirtualPatients();
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

    async deleteVirtualPatient(id: string): Promise<boolean> {
      const res = await axiosInstance.delete(`/virtual-patient/${id}`);

      return res && res.status === HttpStatusCode.Ok;
    },

    async saveNewPatient(newPatient: NewPatient): Promise<boolean> {
      console.log(newPatient);
      const virtualPatient = {
          ...(newPatient.id !== undefined && {id: newPatient.id}),
          age: newPatient.characteristic?.age,
          gender: newPatient.characteristic?.gender,
          result: newPatient.characteristic?.diagnostic,
          createdAt: new Date().toISOString(), // Format ISO 8601,
          createdBy: {
            id: authStore.getUserInfo.id,
            username: authStore.getUserInfo.username,
            firstname: authStore.getUserInfo.firstname,
            lastname: authStore.getUserInfo.lastname,
            email: authStore.getUserInfo.email,
            role: authStore.getUserInfo.role
          },
          actions: {
            action: newPatient.questions.map((question) => ({
                type:
                  question.type === QuestionType.CLOSED
                    ? TypeAction.CLOSED_QUESTION
                    : TypeAction.OPENED_QUESTION,
                primaryElement: question.content,
                ...(question.type === QuestionType.CLOSED
                  ? {
                    actionClosedQuestion: {
                      closedAnswer: question.answer,
                      questionLinked: question
                    }
                  }
                  : {
                    actionOpenedQuestion: {
                      openedAnswer: question.answer,
                      questionLinked: question
                    }
                  }),
                ...newPatient.questions.map((question) => ({
                  type: question.type === QuestionType.CLOSED ? TypeAction.CLOSED_QUESTION : TypeAction.OPENED_QUESTION,
                  primaryElement: question.content,
                  ...(question.type === QuestionType.CLOSED
                    ? {
                      actionClosedQuestion: {
                        closedAnswer: question.answer,
                        questionLinked: question,
                      },
                    }
                    : {
                      actionOpenedQuestion: {
                        openedAnswer: question.answer,
                        questionLinked: question,
                      },
                    }),
                })),
                ...newPatient.biology.map(prescription => ({
                  type: TypeAction.BIOLOGY_MICROBIOLOGY_PRESCRIPTION,
                  primaryElement: prescription.content,
                  actionPrescription: {
                    prescription: prescription
                  }
                })),
                ...newPatient.biopsy.map(prescription => ({
                  type: TypeAction.BIOPSIES_PRESCRIPTION,
                  primaryElement: prescription.content,
                  actionPrescription: {
                    prescription: prescription
                  }
                })),
                ...newPatient.imagery.map(prescription => ({
                  type: TypeAction.IMAGING_PRESCRIPTION,
                  primaryElement: prescription.content,
                  actionPrescription: {
                    prescription: prescription
                  }
                })),
              })
            )
          }
        }
      ;


      const virtualPatientXML = parse('VirtualPatient', virtualPatient);

      const res = await axiosInstance.post('/virtual-patient/xml', virtualPatientXML, {
        headers: {
          'Content-Type': 'application/xml'
        }
      });

      return res && res.status === 200;
    },

    parseVirtualPatient(data
      :
      any
    ):
      VirtualPatient {
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
