import { create } from 'zustand';
import { consultationApi } from '../services/api/consultation';
import type { Consultation, ConsultationDocument } from '../services/api/types';

interface ConsultationStore {
  consultation: Consultation | null;
  documents: ConsultationDocument[];
  loading: boolean;
  error: string | null;
  fetchConsultation: (consultationId: string) => Promise<void>;
  startConsultation: (appointmentId: string) => Promise<void>;
  endConsultation: (consultationId: string) => Promise<void>;
  uploadDocument: (file: File, type: 'prescription' | 'medical-record') => Promise<void>;
  updateNotes: (notes: string) => Promise<void>;
}

export const useConsultationStore = create<ConsultationStore>((set, get) => ({
  consultation: null,
  documents: [],
  loading: false,
  error: null,

  fetchConsultation: async (consultationId: string) => {
    try {
      set({ loading: true, error: null });
      const consultation = await consultationApi.getConsultation(consultationId);
      const documents = await consultationApi.getDocuments(consultationId);
      set({ consultation, documents, loading: false });
    } catch (error) {
      set({ error: 'Failed to fetch consultation', loading: false });
    }
  },

  startConsultation: async (appointmentId: string) => {
    try {
      set({ loading: true, error: null });
      const consultation = await consultationApi.startConsultation(appointmentId);
      set({ consultation, loading: false });
    } catch (error) {
      set({ error: 'Failed to start consultation', loading: false });
    }
  },

  endConsultation: async (consultationId: string) => {
    try {
      set({ loading: true, error: null });
      await consultationApi.endConsultation(consultationId);
      set({ consultation: null, documents: [], loading: false });
    } catch (error) {
      set({ error: 'Failed to end consultation', loading: false });
    }
  },

  uploadDocument: async (file: File, type: 'prescription' | 'medical-record') => {
    try {
      const { consultation } = get();
      if (!consultation) return;

      set({ loading: true, error: null });
      const document = await consultationApi.uploadDocument(consultation.id, file, type);
      set(state => ({
        documents: [...state.documents, document],
        loading: false
      }));
    } catch (error) {
      set({ error: 'Failed to upload document', loading: false });
    }
  },

  updateNotes: async (notes: string) => {
    try {
      const { consultation } = get();
      if (!consultation) return;

      set({ loading: true, error: null });
      await consultationApi.updateNotes(consultation.id, notes);
      set(state => ({
        consultation: state.consultation ? { ...state.consultation, notes } : null,
        loading: false
      }));
    } catch (error) {
      set({ error: 'Failed to update notes', loading: false });
    }
  }
}));