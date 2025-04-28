import { useState } from 'react';
import { ArrowLeft, Search, MapPin, Phone, Filter, Star, Clock, Truck, Heart, Siren, Stethoscope, Users, Building2, CheckCircle, AlertTriangle } from 'lucide-react';
import { Link, useNavigate, useParams } from 'react-router-dom';

interface Ambulance {
  id: string;
  name: string;
  hospitalName: string;
  image: string;
  address: string;
  phone: string;
  rating: number;
  availability: 'available' | 'busy';
  division: string;
  district: string;
  upazila: string;
  features: string[];
  price: string;
  responseTime: string;
  type: 'general' | 'icu' | 'freezing';
  services: string[];
  operatingHours: string;
  emergencyService: boolean;
  medicalTeam: {
    doctors: number;
    nurses: number;
    paramedics: number;
  };
  equipments: string[];
}

const ambulances: Ambulance[] = [
  {
    id: '1',
    name: 'Ad-Din Hospital Ambulance',
    hospitalName: 'Ad-Din Hospital',
    image: 'https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'House no-02, Road-4,Sector-6, Uttara, Dhaka',
    phone: '+880 1234-567890',
    rating: 4.8,
    availability: 'available',
    division: 'Dhaka',
    district: 'Gazipur',
    upazila: 'Kaliakair',
    features: ['Oxygen Support', 'Basic Life Support', 'Trained Staff'],
    price: '৳ 2000/trip',
    responseTime: '10-15 mins',
    type: 'general',
    services: ['Patient Transport', 'First Aid', 'Emergency Response'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 1,
      nurses: 2,
      paramedics: 2
    },
    equipments: ['Stretcher', 'Oxygen Cylinder', 'First Aid Kit']
  },
  {
    id: '2',
    name: 'City Hospital ICU Ambulance',
    hospitalName: 'City Hospital',
    image: 'https://images.unsplash.com/photo-1582719471384-894fbb16e074?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Plot-15, Block-B, Bashundhara R/A, Dhaka',
    phone: '+880 1234-567891',
    rating: 4.9,
    availability: 'available',
    division: 'Dhaka',
    district: 'Dhaka',
    upazila: 'Uttara',
    features: ['ICU Equipment', 'Ventilator', 'Medical Team'],
    price: '৳ 5000/trip',
    responseTime: '15-20 mins',
    type: 'icu',
    services: ['ICU Transport', 'Critical Care', 'Emergency Response'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 2,
      nurses: 3,
      paramedics: 2
    },
    equipments: ['Ventilator', 'Monitor', 'Defibrillator']
  },
  {
    id: '3',
    name: 'Square Hospital Ambulance',
    hospitalName: 'Square Hospital',
    image: 'https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Plot-18, Block-C, Gulshan, Dhaka',
    phone: '+880 1234-567892',
    rating: 4.7,
    availability: 'busy',
    division: 'Dhaka',
    district: 'Dhaka',
    upazila: 'Gulshan',
    features: ['Advanced Life Support', 'Medical Team', 'GPS Tracking'],
    price: '৳ 3000/trip',
    responseTime: '20-25 mins',
    type: 'general',
    services: ['Patient Transport', 'Emergency Response', 'Medical Support'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 1,
      nurses: 2,
      paramedics: 1
    },
    equipments: ['Stretcher', 'Oxygen', 'ECG Monitor']
  },
  {
    id: '4',
    name: 'United Hospital Ambulance',
    hospitalName: 'United Hospital',
    image: 'https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Plot-15, Road-71, Gulshan, Dhaka',
    phone: '+880 1234-567893',
    rating: 4.9,
    availability: 'available',
    division: 'Dhaka',
    district: 'Dhaka',
    upazila: 'Gulshan',
    features: ['Advanced Life Support', 'Cardiac Monitor', 'Ventilator'],
    price: '৳ 4000/trip',
    responseTime: '10-15 mins',
    type: 'icu',
    services: ['Critical Care', 'Emergency Response', 'Inter-hospital Transfer'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 2,
      nurses: 2,
      paramedics: 1
    },
    equipments: ['Ventilator', 'Defibrillator', 'Cardiac Monitor']
  },
  {
    id: '5',
    name: 'Evercare Hospital Ambulance',
    hospitalName: 'Evercare Hospital',
    image: 'https://images.unsplash.com/photo-1582719471384-894fbb16e074?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Plot-81, Block-E, Bashundhara R/A, Dhaka',
    phone: '+880 1234-567894',
    rating: 4.8,
    availability: 'available',
    division: 'Dhaka',
    district: 'Dhaka',
    upazila: 'Bashundhara',
    features: ['Advanced Life Support', 'Neonatal Care', 'Trauma Care'],
    price: '৳ 4500/trip',
    responseTime: '15-20 mins',
    type: 'icu',
    services: ['Critical Care', 'Neonatal Transport', 'Trauma Response'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 2,
      nurses: 2,
      paramedics: 2
    },
    equipments: ['Ventilator', 'Incubator', 'Trauma Equipment']
  },
  {
    id: '6',
    name: 'Apollo Hospital Ambulance',
    hospitalName: 'Apollo Hospital',
    image: 'https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Plot-81, Block-E, Bashundhara R/A, Dhaka',
    phone: '+880 1234-567895',
    rating: 4.7,
    availability: 'busy',
    division: 'Dhaka',
    district: 'Dhaka',
    upazila: 'Bashundhara',
    features: ['Basic Life Support', 'Oxygen Support', 'First Aid'],
    price: '৳ 2500/trip',
    responseTime: '20-25 mins',
    type: 'general',
    services: ['Patient Transport', 'First Aid', 'Emergency Response'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 1,
      nurses: 1,
      paramedics: 2
    },
    equipments: ['Stretcher', 'Oxygen Cylinder', 'First Aid Kit']
  },
  {
    id: '7',
    name: 'Labaid Hospital Ambulance',
    hospitalName: 'Labaid Hospital',
    image: 'https://images.unsplash.com/photo-1582719471384-894fbb16e074?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'House-6, Road-4, Dhanmondi, Dhaka',
    phone: '+880 1234-567896',
    rating: 4.6,
    availability: 'available',
    division: 'Dhaka',
    district: 'Dhaka',
    upazila: 'Dhanmondi',
    features: ['Basic Life Support', 'Oxygen Support', 'First Aid'],
    price: '৳ 2200/trip',
    responseTime: '15-20 mins',
    type: 'general',
    services: ['Patient Transport', 'First Aid', 'Emergency Response'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 1,
      nurses: 1,
      paramedics: 1
    },
    equipments: ['Stretcher', 'Oxygen Cylinder', 'First Aid Kit']
  },
  {
    id: '8',
    name: 'Popular Hospital Ambulance',
    hospitalName: 'Popular Hospital',
    image: 'https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Dhanmondi, Dhaka',
    phone: '+880 1234-567897',
    rating: 4.5,
    availability: 'available',
    division: 'Dhaka',
    district: 'Dhaka',
    upazila: 'Dhanmondi',
    features: ['Basic Life Support', 'Oxygen Support', 'First Aid'],
    price: '৳ 2000/trip',
    responseTime: '20-25 mins',
    type: 'general',
    services: ['Patient Transport', 'First Aid', 'Emergency Response'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 1,
      nurses: 1,
      paramedics: 1
    },
    equipments: ['Stretcher', 'Oxygen Cylinder', 'First Aid Kit']
  },
  {
    id: '9',
    name: 'Ibn Sina Hospital Ambulance',
    hospitalName: 'Ibn Sina Hospital',
    image: 'https://images.unsplash.com/photo-1582719471384-894fbb16e074?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'House-48, Road-9/A, Dhanmondi, Dhaka',
    phone: '+880 1234-567898',
    rating: 4.6,
    availability: 'busy',
    division: 'Dhaka',
    district: 'Dhaka',
    upazila: 'Dhanmondi',
    features: ['Advanced Life Support', 'Cardiac Monitor', 'Ventilator'],
    price: '৳ 3500/trip',
    responseTime: '15-20 mins',
    type: 'icu',
    services: ['Critical Care', 'Emergency Response', 'Inter-hospital Transfer'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 1,
      nurses: 2,
      paramedics: 1
    },
    equipments: ['Ventilator', 'Defibrillator', 'Cardiac Monitor']
  },
  {
    id: '10',
    name: 'Green Life Hospital Ambulance',
    hospitalName: 'Green Life Hospital',
    image: 'https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Green Road, Dhaka',
    phone: '+880 1234-567899',
    rating: 4.5,
    availability: 'available',
    division: 'Dhaka',
    district: 'Dhaka',
    upazila: 'Green Road',
    features: ['Basic Life Support', 'Oxygen Support', 'First Aid'],
    price: '৳ 2300/trip',
    responseTime: '20-25 mins',
    type: 'general',
    services: ['Patient Transport', 'First Aid', 'Emergency Response'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 1,
      nurses: 1,
      paramedics: 1
    },
    equipments: ['Stretcher', 'Oxygen Cylinder', 'First Aid Kit']
  },
  {
    id: '11',
    name: 'Dhaka Medical College Hospital Ambulance',
    hospitalName: 'Dhaka Medical College Hospital',
    image: 'https://images.unsplash.com/photo-1582719471384-894fbb16e074?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Secretariat Road, Dhaka',
    phone: '+880 1234-567900',
    rating: 4.3,
    availability: 'available',
    division: 'Dhaka',
    district: 'Dhaka',
    upazila: 'Shahbag',
    features: ['Basic Life Support', 'Oxygen Support', 'First Aid'],
    price: '৳ 1500/trip',
    responseTime: '25-30 mins',
    type: 'general',
    services: ['Patient Transport', 'First Aid', 'Emergency Response'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 1,
      nurses: 1,
      paramedics: 1
    },
    equipments: ['Stretcher', 'Oxygen Cylinder', 'First Aid Kit']
  },
  {
    id: '12',
    name: 'Chittagong Medical College Hospital Ambulance',
    hospitalName: 'Chittagong Medical College Hospital',
    image: 'https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'O.R. Nizam Road, Chittagong',
    phone: '+880 1234-567901',
    rating: 4.4,
    availability: 'available',
    division: 'Chittagong',
    district: 'Chittagong',
    upazila: 'Chittagong City',
    features: ['Basic Life Support', 'Oxygen Support', 'First Aid'],
    price: '৳ 1800/trip',
    responseTime: '20-25 mins',
    type: 'general',
    services: ['Patient Transport', 'First Aid', 'Emergency Response'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 1,
      nurses: 1,
      paramedics: 1
    },
    equipments: ['Stretcher', 'Oxygen Cylinder', 'First Aid Kit']
  },
  {
    id: '13',
    name: 'Rajshahi Medical College Hospital Ambulance',
    hospitalName: 'Rajshahi Medical College Hospital',
    image: 'https://images.unsplash.com/photo-1582719471384-894fbb16e074?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Medical College Road, Rajshahi',
    phone: '+880 1234-567902',
    rating: 4.3,
    availability: 'busy',
    division: 'Rajshahi',
    district: 'Rajshahi',
    upazila: 'Rajshahi City',
    features: ['Basic Life Support', 'Oxygen Support', 'First Aid'],
    price: '৳ 1700/trip',
    responseTime: '25-30 mins',
    type: 'general',
    services: ['Patient Transport', 'First Aid', 'Emergency Response'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 1,
      nurses: 1,
      paramedics: 1
    },
    equipments: ['Stretcher', 'Oxygen Cylinder', 'First Aid Kit']
  },
  {
    id: '14',
    name: 'Khulna Medical College Hospital Ambulance',
    hospitalName: 'Khulna Medical College Hospital',
    image: 'https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Medical College Road, Khulna',
    phone: '+880 1234-567903',
    rating: 4.4,
    availability: 'available',
    division: 'Khulna',
    district: 'Khulna',
    upazila: 'Khulna City',
    features: ['Basic Life Support', 'Oxygen Support', 'First Aid'],
    price: '৳ 1800/trip',
    responseTime: '20-25 mins',
    type: 'general',
    services: ['Patient Transport', 'First Aid', 'Emergency Response'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 1,
      nurses: 1,
      paramedics: 1
    },
    equipments: ['Stretcher', 'Oxygen Cylinder', 'First Aid Kit']
  },
  {
    id: '15',
    name: 'Sylhet MAG Osmani Medical College Hospital Ambulance',
    hospitalName: 'Sylhet MAG Osmani Medical College Hospital',
    image: 'https://images.unsplash.com/photo-1582719471384-894fbb16e074?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Medical College Road, Sylhet',
    phone: '+880 1234-567904',
    rating: 4.3,
    availability: 'available',
    division: 'Sylhet',
    district: 'Sylhet',
    upazila: 'Sylhet City',
    features: ['Basic Life Support', 'Oxygen Support', 'First Aid'],
    price: '৳ 1900/trip',
    responseTime: '25-30 mins',
    type: 'general',
    services: ['Patient Transport', 'First Aid', 'Emergency Response'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 1,
      nurses: 1,
      paramedics: 1
    },
    equipments: ['Stretcher', 'Oxygen Cylinder', 'First Aid Kit']
  },
  {
    id: '16',
    name: 'Barisal Medical College Hospital Ambulance',
    hospitalName: 'Barisal Medical College Hospital',
    image: 'https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Medical College Road, Barisal',
    phone: '+880 1234-567905',
    rating: 4.2,
    availability: 'busy',
    division: 'Barisal',
    district: 'Barisal',
    upazila: 'Barisal City',
    features: ['Basic Life Support', 'Oxygen Support', 'First Aid'],
    price: '৳ 1700/trip',
    responseTime: '25-30 mins',
    type: 'general',
    services: ['Patient Transport', 'First Aid', 'Emergency Response'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 1,
      nurses: 1,
      paramedics: 1
    },
    equipments: ['Stretcher', 'Oxygen Cylinder', 'First Aid Kit']
  },
  {
    id: '17',
    name: 'Rangpur Medical College Hospital Ambulance',
    hospitalName: 'Rangpur Medical College Hospital',
    image: 'https://images.unsplash.com/photo-1582719471384-894fbb16e074?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Medical College Road, Rangpur',
    phone: '+880 1234-567906',
    rating: 4.3,
    availability: 'available',
    division: 'Rangpur',
    district: 'Rangpur',
    upazila: 'Rangpur City',
    features: ['Basic Life Support', 'Oxygen Support', 'First Aid'],
    price: '৳ 1800/trip',
    responseTime: '20-25 mins',
    type: 'general',
    services: ['Patient Transport', 'First Aid', 'Emergency Response'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 1,
      nurses: 1,
      paramedics: 1
    },
    equipments: ['Stretcher', 'Oxygen Cylinder', 'First Aid Kit']
  },
  {
    id: '18',
    name: 'Mymensingh Medical College Hospital Ambulance',
    hospitalName: 'Mymensingh Medical College Hospital',
    image: 'https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Medical College Road, Mymensingh',
    phone: '+880 1234-567907',
    rating: 4.2,
    availability: 'available',
    division: 'Mymensingh',
    district: 'Mymensingh',
    upazila: 'Mymensingh City',
    features: ['Basic Life Support', 'Oxygen Support', 'First Aid'],
    price: '৳ 1700/trip',
    responseTime: '25-30 mins',
    type: 'general',
    services: ['Patient Transport', 'First Aid', 'Emergency Response'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 1,
      nurses: 1,
      paramedics: 1
    },
    equipments: ['Stretcher', 'Oxygen Cylinder', 'First Aid Kit']
  },
  {
    id: '19',
    name: 'Anwer Khan Modern Hospital Ambulance',
    hospitalName: 'Anwer Khan Modern Hospital',
    image: 'https://images.unsplash.com/photo-1582719471384-894fbb16e074?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Dhanmondi, Dhaka',
    phone: '+880 1234-567908',
    rating: 4.6,
    availability: 'available',
    division: 'Dhaka',
    district: 'Dhaka',
    upazila: 'Dhanmondi',
    features: ['Advanced Life Support', 'Cardiac Monitor', 'Ventilator'],
    price: '৳ 3500/trip',
    responseTime: '15-20 mins',
    type: 'icu',
    services: ['Critical Care', 'Emergency Response', 'Inter-hospital Transfer'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 1,
      nurses: 2,
      paramedics: 1
    },
    equipments: ['Ventilator', 'Defibrillator', 'Cardiac Monitor']
  },
  {
    id: '20',
    name: 'Japan Bangladesh Friendship Hospital Ambulance',
    hospitalName: 'Japan Bangladesh Friendship Hospital',
    image: 'https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Dhanmondi, Dhaka',
    phone: '+880 1234-567909',
    rating: 4.7,
    availability: 'busy',
    division: 'Dhaka',
    district: 'Dhaka',
    upazila: 'Dhanmondi',
    features: ['Advanced Life Support', 'Cardiac Monitor', 'Ventilator'],
    price: '৳ 3800/trip',
    responseTime: '15-20 mins',
    type: 'icu',
    services: ['Critical Care', 'Emergency Response', 'Inter-hospital Transfer'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 1,
      nurses: 2,
      paramedics: 1
    },
    equipments: ['Ventilator', 'Defibrillator', 'Cardiac Monitor']
  },
  {
    id: '21',
    name: 'Dhaka Shishu Hospital Ambulance',
    hospitalName: 'Dhaka Shishu Hospital',
    image: 'https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Sher-e-Bangla Nagar, Dhaka',
    phone: '+880 1234-567910',
    rating: 4.6,
    availability: 'available',
    division: 'Dhaka',
    district: 'Dhaka',
    upazila: 'Sher-e-Bangla Nagar',
    features: ['Pediatric Equipment', 'Oxygen Support', 'Neonatal Care'],
    price: '৳ 2500/trip',
    responseTime: '15-20 mins',
    type: 'general',
    services: ['Pediatric Transport', 'Neonatal Transport', 'Emergency Response'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 1,
      nurses: 2,
      paramedics: 1
    },
    equipments: ['Pediatric Stretcher', 'Incubator', 'Oxygen Support']
  },
  {
    id: '22',
    name: 'National Heart Foundation Ambulance',
    hospitalName: 'National Heart Foundation',
    image: 'https://images.unsplash.com/photo-1582719471384-894fbb16e074?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Mirpur, Dhaka',
    phone: '+880 1234-567911',
    rating: 4.8,
    availability: 'available',
    division: 'Dhaka',
    district: 'Dhaka',
    upazila: 'Mirpur',
    features: ['Cardiac Care', 'Advanced Life Support', 'Cardiac Monitor'],
    price: '৳ 3500/trip',
    responseTime: '10-15 mins',
    type: 'icu',
    services: ['Cardiac Transport', 'Critical Care', 'Emergency Response'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 1,
      nurses: 2,
      paramedics: 1
    },
    equipments: ['Cardiac Monitor', 'Defibrillator', 'Ventilator']
  },
  {
    id: '23',
    name: 'Bangladesh Medical College Hospital Ambulance',
    hospitalName: 'Bangladesh Medical College Hospital',
    image: 'https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Dhanmondi, Dhaka',
    phone: '+880 1234-567912',
    rating: 4.5,
    availability: 'busy',
    division: 'Dhaka',
    district: 'Dhaka',
    upazila: 'Dhanmondi',
    features: ['Basic Life Support', 'Oxygen Support', 'First Aid'],
    price: '৳ 2200/trip',
    responseTime: '20-25 mins',
    type: 'general',
    services: ['Patient Transport', 'First Aid', 'Emergency Response'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 1,
      nurses: 1,
      paramedics: 1
    },
    equipments: ['Stretcher', 'Oxygen Cylinder', 'First Aid Kit']
  },
  {
    id: '24',
    name: 'Holy Family Red Crescent Medical College Hospital Ambulance',
    hospitalName: 'Holy Family Red Crescent Medical College Hospital',
    image: 'https://images.unsplash.com/photo-1582719471384-894fbb16e074?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Eskaton, Dhaka',
    phone: '+880 1234-567913',
    rating: 4.4,
    availability: 'available',
    division: 'Dhaka',
    district: 'Dhaka',
    upazila: 'Eskaton',
    features: ['Basic Life Support', 'Oxygen Support', 'First Aid'],
    price: '৳ 2100/trip',
    responseTime: '20-25 mins',
    type: 'general',
    services: ['Patient Transport', 'First Aid', 'Emergency Response'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 1,
      nurses: 1,
      paramedics: 1
    },
    equipments: ['Stretcher', 'Oxygen Cylinder', 'First Aid Kit']
  },
  {
    id: '25',
    name: 'Birdem General Hospital Ambulance',
    hospitalName: 'Birdem General Hospital',
    image: 'https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Shahbag, Dhaka',
    phone: '+880 1234-567914',
    rating: 4.5,
    availability: 'available',
    division: 'Dhaka',
    district: 'Dhaka',
    upazila: 'Shahbag',
    features: ['Advanced Life Support', 'Diabetic Care', 'Cardiac Monitor'],
    price: '৳ 2800/trip',
    responseTime: '15-20 mins',
    type: 'general',
    services: ['Patient Transport', 'Emergency Response', 'Diabetic Care'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 1,
      nurses: 1,
      paramedics: 1
    },
    equipments: ['Stretcher', 'Cardiac Monitor', 'Diabetic Care Kit']
  },
  {
    id: '26',
    name: 'Freezing Ambulance Service',
    hospitalName: 'Central Blood Bank',
    image: 'https://images.unsplash.com/photo-1582719471384-894fbb16e074?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Mohakhali, Dhaka',
    phone: '+880 1234-567915',
    rating: 4.7,
    availability: 'available',
    division: 'Dhaka',
    district: 'Dhaka',
    upazila: 'Mohakhali',
    features: ['Temperature Control', 'Specialized Storage', 'GPS Tracking'],
    price: '৳ 3000/trip',
    responseTime: '15-20 mins',
    type: 'freezing',
    services: ['Organ Transport', 'Blood Transport', 'Vaccine Transport'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 0,
      nurses: 1,
      paramedics: 1
    },
    equipments: ['Temperature Control System', 'Specialized Storage', 'Monitoring System']
  },
  {
    id: '27',
    name: 'National Institute of Neurosciences Ambulance',
    hospitalName: 'National Institute of Neurosciences',
    image: 'https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Agargaon, Dhaka',
    phone: '+880 1234-567916',
    rating: 4.8,
    availability: 'busy',
    division: 'Dhaka',
    district: 'Dhaka',
    upazila: 'Agargaon',
    features: ['Neuro Care', 'Advanced Life Support', 'Specialized Equipment'],
    price: '৳ 4000/trip',
    responseTime: '15-20 mins',
    type: 'icu',
    services: ['Neuro Critical Care', 'Emergency Response', 'Specialized Transport'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 2,
      nurses: 2,
      paramedics: 1
    },
    equipments: ['Neuro Monitoring', 'Ventilator', 'Specialized Equipment']
  },
  {
    id: '28',
    name: 'National Institute of Cardiovascular Diseases Ambulance',
    hospitalName: 'National Institute of Cardiovascular Diseases',
    image: 'https://images.unsplash.com/photo-1582719471384-894fbb16e074?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Sher-e-Bangla Nagar, Dhaka',
    phone: '+880 1234-567917',
    rating: 4.9,
    availability: 'available',
    division: 'Dhaka',
    district: 'Dhaka',
    upazila: 'Sher-e-Bangla Nagar',
    features: ['Cardiac Care', 'Advanced Life Support', 'Cardiac Monitor'],
    price: '৳ 4200/trip',
    responseTime: '10-15 mins',
    type: 'icu',
    services: ['Cardiac Critical Care', 'Emergency Response', 'Specialized Transport'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 2,
      nurses: 2,
      paramedics: 1
    },
    equipments: ['Cardiac Monitor', 'Defibrillator', 'Ventilator']
  },
  {
    id: '29',
    name: 'Dhaka Community Hospital Ambulance',
    hospitalName: 'Dhaka Community Hospital',
    image: 'https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Moghbazar, Dhaka',
    phone: '+880 1234-567918',
    rating: 4.4,
    availability: 'available',
    division: 'Dhaka',
    district: 'Dhaka',
    upazila: 'Moghbazar',
    features: ['Basic Life Support', 'Oxygen Support', 'First Aid'],
    price: '৳ 1800/trip',
    responseTime: '20-25 mins',
    type: 'general',
    services: ['Patient Transport', 'First Aid', 'Emergency Response'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 0,
      nurses: 1,
      paramedics: 1
    },
    equipments: ['Stretcher', 'Oxygen Cylinder', 'First Aid Kit']
  },
  {
    id: '30',
    name: 'Medinova Medical Services Ambulance',
    hospitalName: 'Medinova Medical Services',
    image: 'https://images.unsplash.com/photo-1582719471384-894fbb16e074?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Dhanmondi, Dhaka',
    phone: '+880 1234-567919',
    rating: 4.5,
    availability: 'busy',
    division: 'Dhaka',
    district: 'Dhaka',
    upazila: 'Dhanmondi',
    features: ['Basic Life Support', 'Oxygen Support', 'First Aid'],
    price: '৳ 2200/trip',
    responseTime: '20-25 mins',
    type: 'general',
    services: ['Patient Transport', 'First Aid', 'Emergency Response'],
    operatingHours: '24/7',
    emergencyService: true,
    medicalTeam: {
      doctors: 0,
      nurses: 1,
      paramedics: 1
    },
    equipments: ['Stretcher', 'Oxygen Cylinder', 'First Aid Kit']
  }
];

const divisions = [
  'Dhaka',
  'Chittagong',
  'Rajshahi',
  'Khulna',
  'Barisal',
  'Sylhet',
  'Rangpur',
  'Mymensingh'
];

const districts = {
  'Dhaka': ['Dhaka', 'Gazipur', 'Narayanganj'],
  'Chittagong': ['Chittagong', "Cox's Bazar", 'Comilla']
};

const upazilas = {
  'Dhaka': ['Uttara', 'Gulshan', 'Mirpur', 'Dhanmondi'],
  'Gazipur': ['Gazipur Sadar', 'Tongi', 'Sreepur']
};

export function AmbulanceListPage() {
  const { type } = useParams<{ type: string }>();
  const navigate = useNavigate();
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedDivision, setSelectedDivision] = useState('');
  const [selectedDistrict, setSelectedDistrict] = useState('');
  const [selectedUpazila, setSelectedUpazila] = useState('');
  const [selectedAvailability, setSelectedAvailability] = useState<'all' | 'available' | 'busy'>('all');

  const handleDivisionChange = (division: string) => {
    setSelectedDivision(division);
    setSelectedDistrict('');
    setSelectedUpazila('');
  };

  const handleDistrictChange = (district: string) => {
    setSelectedDistrict(district);
    setSelectedUpazila('');
  };

  const filteredAmbulances = ambulances.filter(ambulance => {
    const matchesSearch = 
      ambulance.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      ambulance.hospitalName.toLowerCase().includes(searchTerm.toLowerCase()) ||
      ambulance.address.toLowerCase().includes(searchTerm.toLowerCase());
    
    const matchesDivision = !selectedDivision || ambulance.division === selectedDivision;
    const matchesDistrict = !selectedDistrict || ambulance.district === selectedDistrict;
    const matchesUpazila = !selectedUpazila || ambulance.upazila === selectedUpazila;
    const matchesAvailability = selectedAvailability === 'all' || ambulance.availability === selectedAvailability;

    return matchesSearch && matchesDivision && matchesDistrict && matchesUpazila && matchesAvailability;
  });

  return (
    <div className="min-h-screen bg-gradient-to-b from-red-50 to-white">
      {/* Hero Section */}
      <div className="relative h-[300px] overflow-hidden">
        <img
          src="https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=2000&q=80"
          alt="Ambulance Service"
          className="w-full h-full object-cover"
        />
        <div className="absolute inset-0 bg-gradient-to-r from-red-900/90 to-red-800/75">
          <div className="container mx-auto px-4 h-full flex items-center">
            <div className="max-w-2xl">
              <Link to="/ambulance" className="flex items-center text-white/80 hover:text-white mb-6">
                <ArrowLeft className="w-5 h-5 mr-2" />
                Back to Ambulance Services
              </Link>
              <h1 className="text-4xl font-bold text-white mb-4">Available Ambulances</h1>
              <p className="text-xl text-white/90">
                Find and book ambulances for emergency medical transport
              </p>
            </div>
          </div>
        </div>
      </div>

      <div className="container mx-auto px-4 -mt-16 relative z-10">
        {/* Search & Filter Card */}
        <div className="bg-white rounded-2xl shadow-lg p-6 mb-12">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
            <div className="relative">
              <Search className="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
              <input
                type="text"
                placeholder="Search ambulances by name or location..."
                className="w-full pl-12 pr-4 py-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </div>
            <select
              className="p-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
              value={selectedAvailability}
              onChange={(e) => setSelectedAvailability(e.target.value as 'all' | 'available' | 'busy')}
            >
              <option value="all">All Availability</option>
              <option value="available">Available Now</option>
              <option value="busy">Currently Busy</option>
            </select>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <select
              className="p-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
              value={selectedDivision}
              onChange={(e) => handleDivisionChange(e.target.value)}
            >
              <option value="">All Divisions</option>
              {divisions.map(division => (
                <option key={division} value={division}>{division}</option>
              ))}
            </select>

            <select
              className="p-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
              value={selectedDistrict}
              onChange={(e) => handleDistrictChange(e.target.value)}
              disabled={!selectedDivision}
            >
              <option value="">All Districts</option>
              {selectedDivision && districts[selectedDivision as keyof typeof districts]?.map(district => (
                <option key={district} value={district}>{district}</option>
              ))}
            </select>

            <select
              className="p-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
              value={selectedUpazila}
              onChange={(e) => setSelectedUpazila(e.target.value)}
              disabled={!selectedDistrict}
            >
              <option value="">All Upazilas</option>
              {selectedDistrict && upazilas[selectedDistrict as keyof typeof upazilas]?.map(upazila => (
                <option key={upazila} value={upazila}>{upazila}</option>
              ))}
            </select>
          </div>
        </div>

        {/* Statistics */}
        <div className="grid grid-cols-2 md:grid-cols-4 gap-6 mb-12">
          <div className="bg-white rounded-xl p-6 shadow-sm">
            <div className="flex items-center gap-4">
              <div className="bg-red-50 p-3 rounded-xl">
                <Truck className="text-red-500" size={24} />
              </div>
              <div>
                <div className="text-2xl font-bold text-red-600">50+</div>
                <div className="text-gray-600">Ambulances</div>
              </div>
            </div>
          </div>
          <div className="bg-white rounded-xl p-6 shadow-sm">
            <div className="flex items-center gap-4">
              <div className="bg-green-50 p-3 rounded-xl">
                <Users className="text-green-500" size={24} />
              </div>
              <div>
                <div className="text-2xl font-bold text-green-600">100+</div>
                <div className="text-gray-600">Medical Staff</div>
              </div>
            </div>
          </div>
          <div className="bg-white rounded-xl p-6 shadow-sm">
            <div className="flex items-center gap-4">
              <div className="bg-blue-50 p-3 rounded-xl">
                <Building2 className="text-blue-500" size={24} />
              </div>
              <div>
                <div className="text-2xl font-bold text-blue-600">20+</div>
                <div className="text-gray-600">Hospitals</div>
              </div>
            </div>
          </div>
          <div className="bg-white rounded-xl p-6 shadow-sm">
            <div className="flex items-center gap-4">
              <div className="bg-purple-50 p-3 rounded-xl">
                <Heart className="text-purple-500" size={24} />
              </div>
              <div>
                <div className="text-2xl font-bold text-purple-600">24/7</div>
                <div className="text-gray-600">Service</div>
              </div>
            </div>
          </div>
        </div>

        {/* Ambulance List */}
        <div className="space-y-6">
          {filteredAmbulances.length === 0 ? (
            <div className="text-center py-12 bg-white rounded-xl shadow-sm">
              <Truck size={48} className="text-gray-400 mx-auto mb-4" />
              <p className="text-gray-500 mb-2">No ambulances found matching your criteria</p>
              <button
                onClick={() => {
                  setSearchTerm('');
                  setSelectedDivision('');
                  setSelectedDistrict('');
                  setSelectedUpazila('');
                  setSelectedAvailability('all');
                }}
                className="text-red-500 hover:text-red-600"
              >
                Clear all filters
              </button>
            </div>
          ) : (
            filteredAmbulances.map((ambulance) => (
              <div
                key={ambulance.id}
                className="bg-white rounded-xl shadow-sm overflow-hidden hover:shadow-md transition-all cursor-pointer"
                onClick={() => navigate(`/ambulance/book/${type}/${ambulance.id}`)}
              >
                <div className="flex flex-col md:flex-row">
                  <div className="relative w-full md:w-72 h-48">
                    <img
                      src={ambulance.image}
                      alt={ambulance.name}
                      className="w-full h-full object-cover"
                    />
                    <div className="absolute top-4 right-4">
                      <span className={`px-3 py-1 rounded-full text-sm ${
                        ambulance.availability === 'available'
                          ? 'bg-green-500 text-white'
                          : 'bg-red-500 text-white'
                      }`}>
                        {ambulance.availability === 'available' ? 'Available Now' : 'Currently Busy'}
                      </span>
                    </div>
                  </div>
                  <div className="flex-grow p-6">
                    <div className="flex justify-between items-start mb-4">
                      <div>
                        <h3 className="text-xl font-semibold mb-1">{ambulance.name}</h3>
                        <p className="text-blue-500">{ambulance.hospitalName}</p>
                      </div>
                      <div className="flex items-center gap-2 bg-yellow-50 px-3 py-1 rounded-full">
                        <Star className="text-yellow-500" size={18} fill="currentColor" />
                        <span className="font-semibold">{ambulance.rating}</span>
                      </div>
                    </div>

                    <div className="grid grid-cols-2 gap-4 mb-4">
                      <div className="bg-blue-50 p-3 rounded-lg">
                        <div className="font-semibold text-blue-600">{ambulance.responseTime}</div>
                        <div className="text-sm text-gray-600">Response Time</div>
                      </div>
                      <div className="bg-green-50 p-3 rounded-lg">
                        <div className="font-semibold text-green-600">{ambulance.price}</div>
                        <div className="text-sm text-gray-600">Starting Price</div>
                      </div>
                    </div>

                    <div className="space-y-2 text-sm text-gray-600 mb-4">
                      <div className="flex items-center gap-2">
                        <MapPin size={16} />
                        <span>{ambulance.address}</span>
                      </div>
                      <div className="flex items-center gap-2">
                        <Phone size={16} />
                        <span>{ambulance.phone}</span>
                      </div>
                      <div className="flex items-center gap-2">
                        <Clock size={16} />
                        <span>{ambulance.operatingHours}</span>
                      </div>
                    </div>

                    <div className="flex flex-wrap gap-2 mb-4">
                      {ambulance.features.map((feature, index) => (
                        <span
                          key={index}
                          className="px-3 py-1 bg-gray-100 text-gray-600 rounded-full text-sm"
                        >
                          {feature}
                        </span>
                      ))}
                    </div>

                    <div className="flex items-center justify-between pt-4 border-t">
                      <div className="flex gap-4">
                        {ambulance.emergencyService && (
                          <span className="flex items-center gap-1 text-red-500">
                            <Siren size={16} />
                            24/7 Emergency
                          </span>
                        )}
                        <span className="flex items-center gap-1 text-blue-500">
                          <Stethoscope size={16} />
                          Medical Team: {ambulance.medicalTeam.doctors + ambulance.medicalTeam.nurses + ambulance.medicalTeam.paramedics}
                        </span>
                      </div>
                      <button className="px-6 py-2 bg-red-500 text-white rounded-full hover:bg-red-600 transition-colors">
                        View Details
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            ))
          )}
        </div>
      </div>
    </div>
  );
}