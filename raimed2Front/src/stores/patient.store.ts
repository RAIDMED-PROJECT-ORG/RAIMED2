import type { VirtualPatient } from '@/models/virtual-patient/virtualPatient.model';
import { defineStore } from 'pinia';
import axiosInstance from '@/service/httpClient/axios.config';
import type { Gender } from '@/models/virtual-patient/gender.enum';

interface PatientState {
  virtualPatients: VirtualPatient[];
}

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
      return patients;
    },
    parseVirtualPatient(data: any): VirtualPatient {
      return {
        id: String(data.id),
        age: data.age,
        gender: data.gender as Gender,
        createdBy: data.createdBy,
        createdAt: new Date(data.createdAt),
        actions: data.actions,
        result: String(data.result)
      };
    }
  }
});
