import { defineStore } from 'pinia';
import axiosInstance from '@/service/httpClient/axios.config';
import type {Question} from '@/models/question/question.model';

export const useQuestionStore = defineStore('question', {
    actions: {
        async addQuestions(questionsList: Question[] | undefined) {
            const res = await axiosInstance.post('/question/multiples', questionsList);
            if (res && res.status === 200) {
                console.log('Questions saved');
            }
            return;
        }
    }
});
