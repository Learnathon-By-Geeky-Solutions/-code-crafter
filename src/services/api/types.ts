export interface User {
  id: string;
  name: string;
  email: string;
  role: 'patient' | 'doctor';
  avatar?: string;
}

export interface Doctor {
  id: string;
  name: string;
  speciality: string;
  qualification: string;
  experience: string;
  hospital: string;
  rating: number;
  consultations: number;
  availability: string;
  languages: string[];
  awards: string[];
  consultationFee: string;
  waitTime: string;
  location: string;
  verificationBadge: boolean;
  image: string;
}

export interface Appointment {
  id: string;
  doctorId: string;
  patientId: string;
  date: string;
  time: string;
  type: 'upcoming' | 'completed' | 'cancelled';
  mode: 'online' | 'offline';
  status: 'scheduled' | 'completed' | 'cancelled';
  meetingLink?: string;
  prescriptionUrl?: string;
  rating?: number;
  symptoms?: string;
  notes?: string;
}

export interface Consultation {
  id: string;
  appointmentId: string;
  doctorId: string;
  patientId: string;
  meetingLink: string;
  status: 'scheduled' | 'in-progress' | 'completed';
  startTime?: string;
  endTime?: string;
  notes?: string;
  prescriptionUrl?: string;
  documents: ConsultationDocument[];
}

export interface ConsultationDocument {
  id: string;
  consultationId: string;
  name: string;
  url: string;
  type: 'prescription' | 'medical-record';
  uploadedBy: 'doctor' | 'patient';
  uploadedAt: string;
}