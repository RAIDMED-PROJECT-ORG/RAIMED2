import type { DiagnosticStatus } from './diagnosticStatus.enum';

export interface Diagnostic {
  id: string;
  diagnosticName: string;
  status: DiagnosticStatus;
  userId: string;
  virtualPatientId: string;
}
