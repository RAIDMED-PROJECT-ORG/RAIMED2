import { defineStore } from 'pinia';
import axiosInstance from '@/service/httpClient/axios.config';
import type {Prescription} from '@/models/prescription/prescription.model';

export const useQuestionStore = defineStore('question', {
    actions: {
        // Fetch all questions without QuestionType (Open / Closed) filter
        async fetchExistingPrescriptions(teacherId: string | null | undefined): Promise<Prescription[]> {
            let res : Promise<Prescription[]>;
            if (teacherId !== null) {
                res = (await axiosInstance.get(`/prescriptions?teacherId=${teacherId}`)).data;
            }
            else {
                res = (await axiosInstance.get('/prescriptions')).data;
            }
            return res;
        }
    }
});
