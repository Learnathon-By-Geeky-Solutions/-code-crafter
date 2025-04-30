import { useState } from 'react';
import { ArrowLeft, Search, Star, Clock, MapPin, Award, Heart, Brain, Stethoscope, Bone, Baby, Eye, Bluetooth as Tooth, Thermometer, Syringe, Activity, Droplets, Wind, LucideKey as Kidney, Droplet, Target, Sparkles } from 'lucide-react';
import { Link, useParams, useNavigate } from 'react-router-dom';

interface Specialty {
  id: string;
  name: string;
  icon: React.ReactNode;
  description: string;
  doctors: number;
  waitTime: string;
  consultationFee: string;
}

const specialties: Specialty[] = [
  {
    id: 'cardiology',
    name: 'Cardiology',
    icon: <Heart className="w-8 h-8 text-red-500" />,
    description: 'Heart and cardiovascular system specialists',
    doctors: 45,
    waitTime: '10-15 mins',
    consultationFee: '৳1000'
  },
  {
    id: 'neurology',
    name: 'Neurology',
    icon: <Brain className="w-8 h-8 text-purple-500" />,
    description: 'Brain, spine, and nervous system specialists',
    doctors: 32,
    waitTime: '15-20 mins',
    consultationFee: '৳1200'
  },
  {
    id: 'medicine',
    name: 'Medicine',
    icon: <Stethoscope className="w-8 h-8 text-blue-500" />,
    description: 'General medicine and internal medicine specialists',
    doctors: 58,
    waitTime: '5-10 mins',
    consultationFee: '৳800'
  },
  {
    id: 'orthopedics',
    name: 'Orthopedics',
    icon: <Bone className="w-8 h-8 text-green-500" />,
    description: 'Bone and joint specialists',
    doctors: 28,
    waitTime: '20-25 mins',
    consultationFee: '৳1100'
  },
  {
    id: 'pediatrics',
    name: 'Pediatrics',
    icon: <Baby className="w-8 h-8 text-pink-500" />,
    description: 'Child healthcare specialists',
    doctors: 42,
    waitTime: '10-15 mins',
    consultationFee: '৳900'
  },
  {
    id: 'ophthalmology',
    name: 'Ophthalmology',
    icon: <Eye className="w-8 h-8 text-amber-500" />,
    description: 'Eye care specialists',
    doctors: 25,
    waitTime: '25-30 mins',
    consultationFee: '৳1000'
  },
  {
    id: 'dental',
    name: 'Dental',
    icon: <Tooth className="w-8 h-8 text-cyan-500" />,
    description: 'Dental care specialists',
    doctors: 35,
    waitTime: '15-20 mins',
    consultationFee: '৳900'
  },
  {
    id: 'fever',
    name: 'Fever & Flu',
    icon: <Thermometer className="w-8 h-8 text-orange-500" />,
    description: 'Fever, flu, and infectious disease specialists',
    doctors: 48,
    waitTime: '5-10 mins',
    consultationFee: '৳800'
  },
  {
    id: 'vaccination',
    name: 'Vaccination',
    icon: <Syringe className="w-8 h-8 text-teal-500" />,
    description: 'Vaccination and immunization specialists',
    doctors: 22,
    waitTime: '10-15 mins',
    consultationFee: '৳700'
  },
  {
    id: 'endocrinology',
    name: 'Endocrinology',
    icon: <Activity className="w-8 h-8 text-orange-500" />,
    description: 'Hormone and metabolic disorders specialists',
    doctors: 28,
    waitTime: '15-20 mins',
    consultationFee: '৳1200'
  },
  {
    id: 'gastroenterology',
    name: 'Gastroenterology',
    icon: <Droplets className="w-8 h-8 text-purple-500" />,
    description: 'Digestive system specialists',
    doctors: 32,
    waitTime: '20-25 mins',
    consultationFee: '৳1100'
  },
  {
    id: 'pulmonology',
    name: 'Pulmonology',
    icon: <Wind className="w-8 h-8 text-blue-500" />,
    description: 'Respiratory system specialists',
    doctors: 25,
    waitTime: '15-20 mins',
    consultationFee: '৳1000'
  },
  {
    id: 'nephrology',
    name: 'Nephrology',
    icon: <Kidney className="w-8 h-8 text-pink-500" />,
    description: 'Kidney disease specialists',
    doctors: 30,
    waitTime: '20-25 mins',
    consultationFee: '৳1200'
  },
  {
    id: 'rheumatology',
    name: 'Rheumatology',
    icon: <Bone className="w-8 h-8 text-amber-500" />,
    description: 'Joint and autoimmune disease specialists',
    doctors: 22,
    waitTime: '25-30 mins',
    consultationFee: '৳1100'
  },
  {
    id: 'hematology',
    name: 'Hematology',
    icon: <Droplet className="w-8 h-8 text-red-500" />,
    description: 'Blood disorders specialists',
    doctors: 18,
    waitTime: '20-25 mins',
    consultationFee: '৳1200'
  },
  {
    id: 'oncology',
    name: 'Oncology',
    icon: <Target className="w-8 h-8 text-purple-500" />,
    description: 'Cancer treatment specialists',
    doctors: 35,
    waitTime: '15-20 mins',
    consultationFee: '৳1500'
  },
  {
    id: 'dermatology',
    name: 'Dermatology',
    icon: <Sparkles className="w-8 h-8 text-yellow-500" />,
    description: 'Skin specialists',
    doctors: 40,
    waitTime: '20-25 mins',
    consultationFee: '৳1000'
  },
  {
    id: 'psychiatry',
    name: 'Psychiatry',
    icon: <Brain className="w-8 h-8 text-blue-500" />,
    description: 'Mental health specialists',
    doctors: 28,
    waitTime: '30-35 mins',
    consultationFee: '৳1200'
  }
];

export function SpecialtySelectionPage() {
  const { type } = useParams<{ type: string }>();
  const navigate = useNavigate();
  const [searchTerm, setSearchTerm] = useState('');

  const filteredSpecialties = specialties.filter(specialty =>
    specialty.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
    specialty.description.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Hero Section */}
      <div className="relative h-[300px] overflow-hidden">
        <img
          src={type === 'emergency' 
            ? "https://images.unsplash.com/photo-1584982751601-97dcc096659c?auto=format&fit=crop&w=2000&q=80"
            : "https://images.unsplash.com/photo-1666214280557-f1b5022eb634?auto=format&fit=crop&w=2000&q=80"
          }
          alt="Consultation"
          className="w-full h-full object-cover"
        />
        <div className={`absolute inset-0 ${
          type === 'emergency'
            ? 'bg-gradient-to-r from-red-900/90 to-red-900/75'
            : 'bg-gradient-to-r from-blue-900/90 to-blue-900/75'
        }`}>
          <div className="container mx-auto px-4 h-full flex items-center">
            <div className="max-w-2xl">
              <Link to="/appointment" className="flex items-center text-white/80 hover:text-white mb-6">
                <ArrowLeft className="w-5 h-5 mr-2" />
                Back
              </Link>
              <h1 className="text-4xl font-bold text-white mb-4">
                {type === 'emergency' ? 'Emergency Consultation' : 'Specialized Consultation'}
              </h1>
              <p className="text-xl text-white/90">
                {type === 'emergency'
                  ? 'Get immediate medical attention from our on-call doctors'
                  : 'Choose your specialist for a detailed consultation'
                }
              </p>
            </div>
          </div>
        </div>
      </div>

      <div className="container mx-auto px-4 py-12">
        <div className="max-w-6xl mx-auto">
          {/* Search Bar */}
          <div className="relative mb-12">
            <Search className="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
            <input
              type="text"
              placeholder="Search by specialty or description..."
              className="w-full pl-12 pr-4 py-4 rounded-xl bg-white border-none shadow-sm focus:ring-2 focus:ring-blue-200 focus:outline-none"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          </div>

          {/* Specialties Grid */}
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {filteredSpecialties.map((specialty) => (
              <div
                key={specialty.id}
                className="bg-white rounded-xl shadow-sm hover:shadow-md transition-shadow cursor-pointer"
                onClick={() => navigate(`/appointment/${type}/${specialty.id}/doctors`)}
              >
                <div className="p-6">
                  <div className="flex items-start gap-4">
                    <div className={`p-3 rounded-xl ${
                      type === 'emergency' ? 'bg-red-50' : 'bg-blue-50'
                    }`}>
                      {specialty.icon}
                    </div>
                    <div className="flex-grow">
                      <h3 className="text-lg font-semibold mb-1">{specialty.name}</h3>
                      <p className="text-gray-600 text-sm mb-4">{specialty.description}</p>
                      <div className="grid grid-cols-2 gap-4">
                        <div className="flex items-center gap-2 text-sm text-gray-600">
                          <Award size={16} />
                          <span>{specialty.doctors} Doctors</span>
                        </div>
                        <div className="flex items-center gap-2 text-sm text-gray-600">
                          <Clock size={16} />
                          <span>{specialty.waitTime}</span>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div className={`px-6 py-4 border-t flex items-center justify-between ${
                  type === 'emergency' ? 'bg-red-50' : 'bg-blue-50'
                }`}>
                  <span className="font-semibold">From {specialty.consultationFee}</span>
                  <button className={`px-4 py-2 rounded-full text-white ${
                    type === 'emergency' ? 'bg-red-500 hover:bg-red-600' : 'bg-blue-500 hover:bg-blue-600'
                  } transition-colors`}>
                    Book Now
                  </button>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}