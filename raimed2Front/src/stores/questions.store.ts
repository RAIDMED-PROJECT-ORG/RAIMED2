import { defineStore } from 'pinia';
import axiosInstance from '@/service/httpClient/axios.config';
import type {Question} from '@/models/question/question.model';
import axios from 'axios';
import type {QuestionFilter} from '@/models/question/questionFilter.enum';

export const useQuestionStore = defineStore('question', {
    actions: {
        // Fetch all questions without QuestionType (Open / Closed) filter
        async fetchExistingQuestions(teacherId: string | null | undefined, questionType: string | null | undefined, gender: QuestionFilter | null): Promise<Question[]> {
            // let res : Promise<Question[]>;
            const res = (await axiosInstance.get(`/question?teacherId=${teacherId}&questionType=${questionType}&gender=${gender}`)).data;
            // if (teacherId !== null) {
            //     if (questionType !== null){
            //         res = (await axiosInstance.get(`/question?teacherId=${teacherId}&questionType=${questionType}`)).data;
            //     }
            //     res = (await axiosInstance.get(`/question?teacherId=${teacherId}`)).data;
            // }
            // else {
            //     if (questionType !== null){
            //         res = (await axiosInstance.get('/question')).data;
            //     }
            //     else{
            //         res = (await axiosInstance.get('/question')).data;
            //     }
            // }
            return res;
        }
    }
});
