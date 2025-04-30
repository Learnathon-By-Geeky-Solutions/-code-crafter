import axios from '../../lib/axios';
import type { Consultation, ConsultationDocument } from './types';

export const consultationApi = {
  // Get consultation details
  getConsultation: async (consultationId: string): Promise<Consultation> => {
    const response = await axios.get(`/consultations/${consultationId}`);
    return response.data;
  },

  // Start consultation
  startConsultation: async (appointmentId: string): Promise<Consultation> => {
    const response = await axios.post('/consultations/start', { appointmentId });
    return response.data;
  },

  // End consultation
  endConsultation: async (consultationId: string): Promise<void> => {
    await axios.post(`/consultations/${consultationId}/end`);
  },

  // Upload document
  uploadDocument: async (
    consultationId: string,
    file: File,
    type: 'prescription' | 'medical-record'
  ): Promise<ConsultationDocument> => {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('type', type);

    const response = await axios.post(
      `/consultations/${consultationId}/documents`,
      formData,
      {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      }
    );
    return response.data;
  },

  // Update consultation notes
  updateNotes: async (consultationId: string, notes: string): Promise<void> => {
    await axios.put(`/consultations/${consultationId}/notes`, { notes });
  },

  // Get consultation documents
  getDocuments: async (consultationId: string): Promise<ConsultationDocument[]> => {
    const response = await axios.get(`/consultations/${consultationId}/documents`);
    return response.data;
  }
};