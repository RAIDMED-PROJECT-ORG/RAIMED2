import { defineStore } from 'pinia';
import axiosInstance from '@/service/httpClient/axios.config';
import type {Question} from '@/models/question/question.model';
import axios from 'axios';
import type {QuestionFilter} from '@/models/question/questionFilter.enum';

export const useQuestionStore = defineStore('question', {
    actions: {
        // Fetch all questions without QuestionType (Open / Closed) filter
        async fetchExistingQuestions(teacherId: string | null | undefined, questionType: string | null | undefined, gender: QuestionFilter | null): Promise<Question[]> {
            let baseUrl = `/question?teacherId=${teacherId}&gender=${gender}`;
            if (questionType) {
                baseUrl += `&type=${questionType}`;
            }
            const res = (await axiosInstance.get(baseUrl)).data;
            return res;
        }
    }
});
