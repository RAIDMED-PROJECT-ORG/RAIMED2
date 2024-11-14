import type { VirtualPatient } from '@/models/virtual-patient/virtualPatient.model';
import type { StoreDefinition } from 'pinia';
import { defineStore } from 'pinia';
import axiosInstance from '@/service/httpClient/axios.config';

interface PatientState {
  virtualPatients: VirtualPatient[];
}

export const usePatientStore: StoreDefinition<PatientState> = defineStore('patient', {
  state: (): PatientState => ({
    virtualPatients: [] as VirtualPatient[]
  }),
  getters:{
    getVirtualPatients(): VirtualPatient[] {
      return this.virtualPatients;
    }
  },
  actions:{
    async init() {
      this.virtualPatients = []
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
        gender: data.gender as Gender, // Assuming the gender is a valid Gender value
        createdBy: data.createdBy,
        createdAt: new Date(data.createdAt), // Parses date string to Date object
        result: String(data.result)
      };
    }
  }
})