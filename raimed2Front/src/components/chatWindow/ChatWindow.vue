<script setup lang="ts">
import ChatBubble from '@/components/chatWindow/ChatBubble.vue';
import { AuthorChat } from '@/models/chat/authorChat.enum';
import type { ChatMessage } from '@/models/chat/chatMessage.model';
import { useDiagnosticStore } from '@/stores/diagnostic.store';
import { storeToRefs } from 'pinia';
import { nextTick, ref, watch } from 'vue';
const props = defineProps<{
  chatMessages: ChatMessage[];
}>();

const { getVirtualPatientDetails } = storeToRefs(useDiagnosticStore());

const chatContainerRef = ref<HTMLElement | null>(null);

// Scroll to the bottom of the chat container when a new message is added
watch(
  () => props.chatMessages,
  () => nextTick(() => chatContainerRef.value?.scrollTo(0, chatContainerRef.value?.scrollHeight))
);
</script>

<template>
  <div class="w-full card items-center bg-base-100 rounded-lg shadow-lg border">
    <div class="w-full h-10 min-h-10 flex justify-center items-center bg-slate-50 rounded-lg">
      <h2 class="font-bold">Discussion</h2>
    </div>
    <div class="w-full max-h-full h-full p-4 overflow-auto" ref="chatContainerRef">
      <ChatBubble
        :author="AuthorChat.STUDENT"
        :content="`Bonjour ${getVirtualPatientDetails.title} (${getVirtualPatientDetails.age} ans) pouvez-vous me dire ce qui vous amène en consultation ?`"
      />
      <ChatBubble
        v-for="chatMessage in chatMessages"
        v-bind:key="chatMessage.eventId + '-' + chatMessage.author"
        :author="chatMessage.author"
        :content="chatMessage.content"
      />
    </div>
  </div>
</template>
