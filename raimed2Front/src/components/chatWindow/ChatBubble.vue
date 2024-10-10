<script setup lang="ts">
import { AuthorChat, getAuthorChatDisplayName } from '@/models/chat/authorChat.enum';
defineProps<{
  author: AuthorChat;
  content: string;
}>();
</script>
<template>
  <div
    class="chat"
    :class="{
      'chat-start': author === AuthorChat.VIRTUAL_PATIENT,
      'chat-end': author === AuthorChat.STUDENT
    }"
  >
    <div class="chat-header">{{ getAuthorChatDisplayName(author) }}</div>
    <div class="chat-bubble">{{ content }}</div>
  </div>
</template>
@/models/chat/authorChat.enum
