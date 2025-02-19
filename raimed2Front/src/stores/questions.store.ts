import { defineStore } from 'pinia';
import axiosInstance from '@/service/httpClient/axios.config';
import type {Question} from '@/models/question/question.model';
import axios from 'axios';
import type {QuestionFilter} from '@/models/question/questionFilter.enum';

export const useQuestionStore = defineStore('question', {
    actions: {
        // Fetch all questions without QuestionType (Open / Closed) filter
        async fetchExistingQuestions(teacherId: string | null | undefined, questionType: string | null | undefined, gender: QuestionFilter | null): Promise<Question[]> {
            const res = (await axiosInstance.get(`/question?teacherId=${teacherId}&questionType=${questionType}&gender=${gender}`)).data;
            return res;
        }
    }
});
