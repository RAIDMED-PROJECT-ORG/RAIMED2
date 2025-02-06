<script setup lang="ts">
import { ref, computed } from 'vue';
import { useQuery } from '@tanstack/vue-query';
import { useMutation } from '@tanstack/vue-query';
import axiosInstance from '@/service/httpClient/axios.config';
import type { Question } from '@/models/question/question.model';
import { QuestionType, QuestionTypeDisplayNames } from '@/models/question/questionType.enum';
import type { CreateQuestionDto } from '@/models/question/createQuestion.dto';
import type { UpdateQuestionDto } from '@/models/question/updateQuestion.dto';
import AuthenticatedViewLayout from '@/layouts/AuthenticatedViewLayout.vue';
import QuestionManagement from '@/components/questionManagement/QuestionManagement.vue';
import CreateQuestionForm from '@/components/questionManagement/createQuestionForm.vue';

// Manage tabs which display questions by type
const activeTab = ref<QuestionType>(QuestionType.OPENED);
const handleOnChangeTab = (type: QuestionType) => {
  activeTab.value = type;
};

/**
 * Define the query to get all questions using vue-query
 */
const queryAllQuestion = useQuery<Question[]>({
  queryKey: ['getAllQuestion'],
  queryFn: () => axiosInstance.get<Question[]>('/question').then((res) => res.data),
  refetchOnWindowFocus: false,
  retry: false
});

/**
 * Define the mutation to add a question using vue-query
 */
const mutationAddQuestion = useMutation({
  mutationFn: (createQuestionDto: CreateQuestionDto) =>
    axiosInstance.post('/question', createQuestionDto),
  // If the mutation succeeds, refetch the questions
  onSuccess: () => queryAllQuestion.refetch()
});

/**
 * Define the mutation to update a question using vue-query
 */
const mutationUpdateQuestion = useMutation({
  mutationFn: (question: Question) => {
    const updateQuestionDto: UpdateQuestionDto = {
      content: question.content,
      filter: question.filter,
      type: question.type
    };
    return axiosInstance.put(`/question/${question.id}`, updateQuestionDto);
  },
  // If the mutation succeeds, refetch the questions
  onSuccess: () => queryAllQuestion.refetch()
});

/**
 * Define the mutation to delete a question using vue-query
 */
const mutationDeleteQuestion = useMutation({
  mutationFn: (questionId: string) => axiosInstance.delete(`/question/${questionId}`),
  // If the mutation succeeds, refetch the questions
  onSuccess: () => queryAllQuestion.refetch()
});

/**
 * Define a computed property to display the loader when a query or a mutation is pending
 */
const isLoaderDisplayed = computed<boolean>(
  () =>
    queryAllQuestion.isLoading.value ||
    queryAllQuestion.isFetching.value ||
    mutationAddQuestion.isPending.value ||
    mutationUpdateQuestion.isPending.value ||
    mutationDeleteQuestion.isPending.value
);
</script>

<template>
  <AuthenticatedViewLayout title="Catalogue de questions">
    <div class="w-full h-full flex flex-col justify-start items-center">
      <!-- Tabs -->
      <div role="tablist" class="tabs tabs-boxed mb-6">
        <a
          v-for="tab in Object.values(QuestionType)"
          v-bind:key="tab"
          role="tab"
          :class="`tab ${tab === activeTab && 'tab-active'}`"
          @click="handleOnChangeTab(tab)"
        >
          {{ QuestionTypeDisplayNames[tab] }}
        </a>
      </div>
      <!-- Question management list -->
      <QuestionManagement
        :isLoading="isLoaderDisplayed"
        :is-error="queryAllQuestion.isError.value"
        :questionList="
          queryAllQuestion.data?.value?.filter((question) => question.type === activeTab) ?? []
        "
        :type="activeTab"
        :updateQuestion="mutationUpdateQuestion.mutate"
        :deleteQuestion="mutationDeleteQuestion.mutate"
      />
      <!-- Question creation form -->
      <CreateQuestionForm
        :isLoading="isLoaderDisplayed"
        :type="activeTab"
        :createQuestion="mutationAddQuestion.mutate"
      />
    </div>
  </AuthenticatedViewLayout>
</template>
