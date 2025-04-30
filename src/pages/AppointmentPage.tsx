import { useState } from 'react';
import { ArrowLeft, Video, Calendar, Star, Clock, MapPin, ArrowRight, Award, ThumbsUp, Users } from 'lucide-react';
import { Link } from 'react-router-dom';

type AppointmentType = 'emergency' | 'specialized';

interface ConsultationType {
  id: AppointmentType;
  title: string;
  description: string;
  image: string;
  icon: React.ReactNode;
  features: string[];
  price: string;
  waitTime: string;
}

interface TopDoctor {
  id: string;
  name: string;
  image: string;
  specialty: string;
  hospital: string;
  experience: string;
  rating: number;
  consultations: number;
  availability: string;
  awards: string[];
}

export function AppointmentPage() {
  const [appointmentType, setAppointmentType] = useState<AppointmentType | null>(null);

  const consultationTypes: ConsultationType[] = [
    {
      id: 'emergency',
      title: 'Emergency Consultation',
      description: 'Get immediate medical attention from our on-call doctors',
      image: 'https://images.unsplash.com/photo-1584982751601-97dcc096659c?auto=format&fit=crop&w=800&h=400&q=80',
      icon: <Video className="w-8 h-8 text-red-500" />,
      features: [
        'Instant doctor connection',
        '24/7 availability',
        'Video consultation',
        'Digital prescription',
        'Follow-up included'
      ],
      price: '৳500',
      waitTime: '< 5 mins'
    },
    {
      id: 'specialized',
      title: 'Specialized Consultation',
      description: 'Book an appointment with our specialist doctors',
      image: 'https://images.unsplash.com/photo-1666214280557-f1b5022eb634?auto=format&fit=crop&w=800&h=400&q=80',
      icon: <Calendar className="w-8 h-8 text-blue-500" />,
      features: [
        'Choose your specialist',
        'Flexible scheduling',
        'Detailed consultation',
        'Treatment plan',
        'Regular follow-up'
      ],
      price: '৳1000',
      waitTime: 'Schedule based'
    }
  ];

  const topDoctors: TopDoctor[] = [
    {
      id: '1',
      name: 'Dr. Sarah Johnson',
      image: 'https://images.unsplash.com/photo-1559839734-2b71ea197ec2?auto=format&fit=crop&w=300&h=300&q=80',
      specialty: 'Cardiologist',
      hospital: 'City Heart Center',
      experience: '15+ years',
      rating: 4.9,
      consultations: 15000,
      availability: 'Available Today',
      awards: ['Best Cardiologist 2023', 'Research Excellence Award']
    },
    {
      id: '2',
      name: 'Dr. Michael Chen',
      image: 'https://images.unsplash.com/photo-1622253692010-333f2da6031d?auto=format&fit=crop&w=300&h=300&q=80',
      specialty: 'Neurologist',
      hospital: 'Brain & Spine Institute',
      experience: '12+ years',
      rating: 4.8,
      consultations: 12000,
      availability: 'Available Tomorrow',
      awards: ['Neurology Innovation Award', 'Patient Choice Award']
    },
    {
      id: '3',
      name: 'Dr. Emily Rodriguez',
      image: 'https://images.unsplash.com/photo-1594824476967-48c8b964273f?auto=format&fit=crop&w=300&h=300&q=80',
      specialty: 'Pediatrician',
      hospital: 'Children\'s Medical Center',
      experience: '10+ years',
      rating: 4.9,
      consultations: 18000,
      availability: 'Available Today',
      awards: ['Excellence in Pediatric Care', 'Community Service Award']
    }
  ];

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Hero Section */}
      <div className="relative h-[300px] overflow-hidden">
        <img
          src="https://images.unsplash.com/photo-1576091160550-2173dba999ef?auto=format&fit=crop&w=1920&q=80"
          alt="Appointment"
          className="w-full h-full object-cover"
        />
        <div className="absolute inset-0 bg-gradient-to-r from-blue-900/90 to-blue-900/75">
          <div className="container mx-auto px-4 h-full flex items-center">
            <div className="max-w-2xl">
              <Link to="/" className="flex items-center text-white/80 hover:text-white mb-6">
                <ArrowLeft className="w-5 h-5 mr-2" />
                Back to Home
              </Link>
              <h1 className="text-4xl font-bold text-white mb-4">Book an Appointment</h1>
              <p className="text-xl text-white/90">
                Choose the type of consultation that best suits your needs
              </p>
            </div>
          </div>
        </div>
      </div>

      <div className="max-w-5xl mx-auto px-4 py-12">
        {/* Consultation Types */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8 mb-8">
          {consultationTypes.map((type) => (
            <div
              key={type.id}
              className={`bg-white rounded-2xl shadow-sm overflow-hidden transition-all ${
                appointmentType === type.id
                  ? 'ring-2 ring-blue-500 shadow-lg'
                  : 'hover:shadow-md'
              }`}
              onClick={() => setAppointmentType(type.id)}
            >
              <div className="relative h-48">
                <img
                  src={type.image}
                  alt={type.title}
                  className="w-full h-full object-cover"
                />
                <div className="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent" />
                <div className="absolute bottom-4 left-4 text-white">
                  <div className="flex items-center gap-3 mb-2">
                    {type.icon}
                    <h3 className="text-xl font-semibold">{type.title}</h3>
                  </div>
                  <p className="text-white/90">{type.description}</p>
                </div>
              </div>
              <div className="p-6">
                <div className="grid grid-cols-2 gap-4 mb-6">
                  <div className="bg-blue-50 rounded-lg p-3">
                    <span className="text-sm text-blue-600">Starting from</span>
                    <p className="text-xl font-semibold text-blue-700">{type.price}</p>
                  </div>
                  <div className="bg-green-50 rounded-lg p-3">
                    <span className="text-sm text-green-600">Wait Time</span>
                    <p className="text-xl font-semibold text-green-700">{type.waitTime}</p>
                  </div>
                </div>
                <ul className="space-y-2">
                  {type.features.map((feature, index) => (
                    <li key={index} className="flex items-center gap-2 text-gray-600">
                      <ArrowRight size={16} className="text-blue-500" />
                      {feature}
                    </li>
                  ))}
                </ul>
              </div>
            </div>
          ))}
        </div>

        {appointmentType && (
          <div className="text-center mb-16">
            <Link
              to={`/appointment/${appointmentType}`}
              className="inline-block bg-blue-500 text-white px-8 py-3 rounded-full hover:bg-blue-600 transition-colors"
            >
              Continue
            </Link>
          </div>
        )}

        {/* Top Performing Doctors */}
        <div className="mt-16">
          <div className="text-center mb-12">
            <h2 className="text-3xl font-bold mb-4">Top Performing Doctors</h2>
            <p className="text-gray-600 max-w-2xl mx-auto">
              Meet our highly rated and experienced doctors who have helped thousands of patients
            </p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            {topDoctors.map((doctor) => (
              <div key={doctor.id} className="bg-white rounded-xl shadow-sm overflow-hidden hover:shadow-md transition-all">
                <div className="relative">
                  <img
                    src={doctor.image}
                    alt={doctor.name}
                    className="w-full h-48 object-cover"
                  />
                  <div className="absolute top-4 right-4">
                    <span className={`px-3 py-1 rounded-full text-sm ${
                      doctor.availability.includes('Today')
                        ? 'bg-green-500 text-white'
                        : 'bg-yellow-100 text-yellow-600'
                    }`}>
                      {doctor.availability}
                    </span>
                  </div>
                </div>

                <div className="p-6">
                  <h3 className="text-xl font-semibold mb-2">{doctor.name}</h3>
                  <p className="text-blue-500 font-medium mb-1">{doctor.specialty}</p>
                  <p className="text-gray-600 text-sm mb-4">{doctor.hospital}</p>

                  <div className="grid grid-cols-2 gap-4 mb-6">
                    <div className="flex items-center gap-2">
                      <Star className="text-yellow-500" size={20} fill="currentColor" />
                      <div>
                        <div className="font-semibold">{doctor.rating}</div>
                        <div className="text-xs text-gray-500">Rating</div>
                      </div>
                    </div>
                    <div className="flex items-center gap-2">
                      <Users className="text-blue-500" size={20} />
                      <div>
                        <div className="font-semibold">{(doctor.consultations / 1000).toFixed(1)}k+</div>
                        <div className="text-xs text-gray-500">Consultations</div>
                      </div>
                    </div>
                  </div>

                  <div className="space-y-2 mb-6">
                    <div className="flex items-center gap-2 text-gray-600">
                      <Clock size={16} />
                      <span>{doctor.experience}</span>
                    </div>
                    <div className="flex items-center gap-2">
                      <Award size={16} className="text-yellow-500" />
                      <span className="text-sm text-gray-600">{doctor.awards[0]}</span>
                    </div>
                  </div>

                  <Link
                    to={`/appointment/specialized/${doctor.specialty.toLowerCase()}/doctor/${doctor.id}`}
                    className="block w-full text-center bg-blue-500 text-white py-2 rounded-lg hover:bg-blue-600 transition-colors"
                  >
                    Book Appointment
                  </Link>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}