import type { VirtualPatient } from '@/models/virtual-patient/virtualPatient.model';
import { defineStore } from 'pinia';
import axiosInstance from '@/service/httpClient/axios.config';
import type { Gender } from '@/models/virtual-patient/gender.enum';
import type { NewPatient } from '@/models/new-patient/newPatient.model';
import { TypeAction } from '@/models/virtual-patient/typeAction.enum';
import { QuestionType } from '@/models/question/questionType.enum';

import { parse } from 'js2xmlparser';
import {useAuthStore} from '@/stores/auth.store';

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
    async deleteVirtualPatient(id: string): Promise<boolean> {
      const res = await axiosInstance.delete(`/virtual-patient/${id}`);
      if (res && res.status === 200) {
        return true;
      }
      return false;
    },
    async saveNewPatient(newPatient: NewPatient): Promise<boolean> {

      const virtualPatient = {
        age: newPatient.characteristic?.age,
        gender: newPatient.characteristic?.gender,
        result: newPatient.characteristic?.diagnostic,
        createdAt: new Date().toISOString(), // Format ISO 8601,
        createdBy: {
          id: authStore.getUserInfo.id,
          firstname: authStore.getUserInfo.username,
          lastname: authStore.getUserInfo.lastname,
          email: authStore.getUserInfo.email,
          role: authStore.getUserInfo.role,
        },
        actions: {
          action: newPatient.questions.map((question) =>  ({
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
          }))
        }
      };
      const virtualPatientXML = parse('VirtualPatient', virtualPatient);

      console.log(virtualPatientXML);

      const res = await axiosInstance.post('/virtual-patient/xml', virtualPatientXML, {
        headers: {
          'Content-Type': 'application/xml'
        }
      });
      if (res && res.status === 200) {
        return true;
      }
      return false;
    },
    parseVirtualPatient(data: any): VirtualPatient {
      console.log(data)
      return {
        id: String(data.id),
        age: data.age,
        gender: data.gender as Gender,
        createdBy: data.createdBy,
        createdAt: data.createdAt,
        //createdAt: new Date(data.createdAt).toISOString(),
        actions: data.actions,
        result: data.result
      };
    }
  }
});
