import type { AuthorChat } from './authorChat.enum';

export interface ChatMessage {
  eventId: string;
  author: AuthorChat;
  content: string;
}
