import { defineStore } from 'pinia';
import axiosInstance from '@/service/httpClient/axios.config';
import type {Question} from '@/models/question/question.model';

export const useQuestionStore = defineStore('question', {
    actions: {
        // Fetch all questions without QuestionType (Open / Closed) filter
        async fetchExistingQuestions(teacherId: string | null | undefined): Promise<Question[]> {
            let res : Promise<Question[]>;
            if (teacherId !== null) {
                res = (await axiosInstance.get(`/question?teacherId=${teacherId}`)).data;
            }
            else {
                res = (await axiosInstance.get('/question')).data;
            }
            return res;
        }
    }
});
