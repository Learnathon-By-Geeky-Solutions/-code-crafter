import { useState } from 'react';
import { ArrowLeft, Search, Star, Clock, MapPin, Award, ThumbsUp, Video, Calendar, Filter, Heart } from 'lucide-react';
import { Link, useParams, useNavigate, useLocation } from 'react-router-dom';

interface Doctor {
  id: string;
  name: string;
  image: string;
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
  specializations: string[];
}

const generateDoctors = (count: number): Doctor[] => {
  const doctors: Doctor[] = [];
  const maleNames = [
    'Dr. Masum Jia', 'Dr. Michael Chen', 'Dr. David Kim', 'Dr. John Smith',
    'Dr. Robert Johnson', 'Dr. William Brown', 'Dr. James Wilson', 'Dr. Richard Davis',
    'Dr. Thomas Anderson', 'Dr. Charles Martin', 'Dr. Joseph Taylor', 'Dr. Christopher Lee',
    'Dr. Daniel White', 'Dr. Matthew Harris', 'Dr. Andrew Jackson', 'Dr. Kevin Thompson',
    'Dr. Brian Rodriguez', 'Dr. George Martinez', 'Dr. Edward Robinson', 'Dr. Ronald Clark',
    'Dr. Steven Wright', 'Dr. Kenneth Mitchell', 'Dr. Mark Young', 'Dr. Paul Scott',
    'Dr. Larry King'
  ];

  const femaleNames = [
    'Dr. Sarah Johnson', 'Dr. Emily Rodriguez', 'Dr. Lisa Patel', 'Dr. Maria Garcia',
    'Dr. Jennifer Brown', 'Dr. Michelle Lee', 'Dr. Elizabeth Davis', 'Dr. Patricia Martin',
    'Dr. Linda Anderson', 'Dr. Barbara Wilson', 'Dr. Jessica Thompson', 'Dr. Margaret White',
    'Dr. Susan Harris', 'Dr. Dorothy Jackson', 'Dr. Karen Martinez', 'Dr. Nancy Clark',
    'Dr. Betty Wright', 'Dr. Helen Mitchell', 'Dr. Sandra Young', 'Dr. Donna Scott',
    'Dr. Carol King', 'Dr. Sharon Lee', 'Dr. Ruth Davis', 'Dr. Kimberly Martin',
    'Dr. Deborah Wilson'
  ];

  const qualifications = [
    'MBBS, FCPS', 'MBBS, MD', 'MBBS, MS', 'MBBS, DM', 'MBBS, DNB',
    'MBBS, MRCP', 'MBBS, FRCS', 'MBBS, FACS', 'MBBS, PhD', 'MBBS, DrNB'
  ];

  const hospitals = [
    'City General Hospital', 'Metropolitan Medical Center', 'Central Hospital',
    'United Hospital', 'Apollo Hospital', 'Square Hospital', 'Popular Medical Center',
    'Evercare Hospital', 'Green Life Hospital', 'Ibn Sina Hospital'
  ];

  const maleImages = [
    'https://images.unsplash.com/photo-1537368910025-700350fe46c7?auto=format&fit=crop&w=300&h=300&q=80',
    'https://images.unsplash.com/photo-1622253692010-333f2da6031d?auto=format&fit=crop&w=300&h=300&q=80',
    'https://images.unsplash.com/photo-1612349317150-e413f6a5b16d?auto=format&fit=crop&w=300&h=300&q=80',
    'https://images.unsplash.com/photo-1559839734-2b71ea197ec2?auto=format&fit=crop&w=300&h=300&q=80'
  ];

  const femaleImages = [
    'https://images.unsplash.com/photo-1594824476967-48c8b964273f?auto=format&fit=crop&w=300&h=300&q=80',
    'https://images.unsplash.com/photo-1527613426441-4da17471b66d?auto=format&fit=crop&w=300&h=300&q=80',
    'https://images.unsplash.com/photo-1559839734-2b71ea197ec2?auto=format&fit=crop&w=300&h=300&q=80',
    'https://images.unsplash.com/photo-1527613426441-4da17471b66d?auto=format&fit=crop&w=300&h=300&q=80'
  ];

  const languages = ['English', 'Bengali', 'Hindi', 'Urdu', 'Arabic', 'Chinese'];
  const awards = [
    'Best Doctor Award 2023',
    'Excellence in Healthcare',
    'Outstanding Contribution to Medicine',
    'Research Pioneer Award',
    'Patient Choice Award',
    'Healthcare Innovation Award'
  ];

  for (let i = 0; i < count; i++) {
    const isFemale = Math.random() > 0.5;
    const name = isFemale
      ? femaleNames[Math.floor(Math.random() * femaleNames.length)]
      : maleNames[Math.floor(Math.random() * maleNames.length)];
    
    const experienceYears = Math.floor(Math.random() * 20) + 5;
    const consultationCount = Math.floor(Math.random() * 15000) + 5000;
    
    const doctorLanguages = [...languages]
      .sort(() => Math.random() - 0.5)
      .slice(0, Math.floor(Math.random() * 3) + 2);
    
    const doctorAwards = [...awards]
      .sort(() => Math.random() - 0.5)
      .slice(0, Math.floor(Math.random() * 3) + 1);

    const specializations = [
      'General Consultation',
      'Emergency Care',
      'Chronic Disease Management',
      'Preventive Care',
      'Health Screening'
    ];

    doctors.push({
      id: `doc-${i + 1}`,
      name,
      image: isFemale
        ? femaleImages[Math.floor(Math.random() * femaleImages.length)]
        : maleImages[Math.floor(Math.random() * maleImages.length)],
      speciality: 'Specialist',
      qualification: qualifications[Math.floor(Math.random() * qualifications.length)],
      experience: `${experienceYears} years`,
      hospital: hospitals[Math.floor(Math.random() * hospitals.length)],
      rating: Number((Math.random() * (5 - 4.2) + 4.2).toFixed(1)),
      consultations: consultationCount,
      availability: Math.random() > 0.3 ? 'Available Today' : 'Next Available: Tomorrow',
      languages: doctorLanguages,
      awards: doctorAwards,
      consultationFee: `৳${Math.floor(Math.random() * 500) + 800}`,
      waitTime: `${Math.floor(Math.random() * 20) + 5}-${Math.floor(Math.random() * 20) + 25} mins`,
      location: hospitals[Math.floor(Math.random() * hospitals.length)],
      verificationBadge: Math.random() > 0.2,
      specializations
    });
  }

  return doctors;
};

const doctors = generateDoctors(50);

export default function DoctorListPage() {
  const { type, specialty, hospitalId, departmentId } = useParams();
  const navigate = useNavigate();
  const location = useLocation();
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedExperience, setSelectedExperience] = useState('all');
  const [selectedLanguage, setSelectedLanguage] = useState('all');
  const [selectedAvailability, setSelectedAvailability] = useState('all');

  const isOfflineConsultation = location.pathname.includes('/hospitals/');

  const experienceRanges = [
    { value: 'all', label: 'All Experience' },
    { value: '0-5', label: '0-5 years' },
    { value: '5-10', label: '5-10 years' },
    { value: '10+', label: '10+ years' }
  ];

  const languages = [
    { value: 'all', label: 'All Languages' },
    { value: 'english', label: 'English' },
    { value: 'bengali', label: 'Bengali' },
    { value: 'hindi', label: 'Hindi' }
  ];

  const availability = [
    { value: 'all', label: 'All Availability' },
    { value: 'today', label: 'Available Today' },
    { value: 'tomorrow', label: 'Available Tomorrow' },
    { value: 'week', label: 'This Week' }
  ];

  const filteredDoctors = doctors.filter(doctor => {
    const matchesSearch = 
      doctor.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      doctor.speciality.toLowerCase().includes(searchTerm.toLowerCase()) ||
      doctor.hospital.toLowerCase().includes(searchTerm.toLowerCase());

    const matchesExperience = selectedExperience === 'all' || (
      selectedExperience === '10+' ? parseInt(doctor.experience) >= 10 :
      selectedExperience === '5-10' ? (parseInt(doctor.experience) >= 5 && parseInt(doctor.experience) < 10) :
      parseInt(doctor.experience) < 5
    );

    const matchesLanguage = selectedLanguage === 'all' || 
      doctor.languages.map(l => l.toLowerCase()).includes(selectedLanguage);

    const matchesAvailability = selectedAvailability === 'all' ||
      doctor.availability.toLowerCase().includes(selectedAvailability);

    return matchesSearch && matchesExperience && matchesLanguage && matchesAvailability;
  });

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Hero Section */}
      <div className="relative h-[300px] overflow-hidden">
        <img
          src={type === 'emergency' 
            ? "https://images.unsplash.com/photo-1584982751601-97dcc096659c?auto=format&fit=crop&w=2000&q=80"
            : "https://images.unsplash.com/photo-1666214280557-f1b5022eb634?auto=format&fit=crop&w=2000&q=80"
          }
          alt="Doctors"
          className="w-full h-full object-cover"
        />
        <div className={`absolute inset-0 ${
          type === 'emergency'
            ? 'bg-gradient-to-r from-red-900/90 to-red-900/75'
            : 'bg-gradient-to-r from-blue-900/90 to-blue-900/75'
        }`}>
          <div className="container mx-auto px-4 h-full flex items-center">
            <div className="max-w-2xl">
              <Link 
                to={isOfflineConsultation ? `/hospitals/${hospitalId}/departments` : `/appointment/${type}`}
                className="flex items-center text-white/80 hover:text-white mb-6"
              >
                <ArrowLeft className="w-5 h-5 mr-2" />
                Back
              </Link>
              <h1 className="text-4xl font-bold text-white mb-4">
                {type === 'emergency' ? 'Emergency Doctors' : `${specialty} Specialists`}
              </h1>
              <p className="text-xl text-white/90">
                {type === 'emergency'
                  ? 'Get immediate medical attention from our on-call doctors'
                  : 'Choose from our experienced specialists for consultation'
                }
              </p>
            </div>
          </div>
        </div>
      </div>

      <div className="container mx-auto px-4 py-12">
        <div className="max-w-7xl mx-auto">
          {/* Search and Filters */}
          <div className="bg-white rounded-xl shadow-lg p-6 mb-8">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
              <div className="relative">
                <Search className="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
                <input
                  type="text"
                  placeholder="Search doctors by name, specialty, or hospital..."
                  className="w-full pl-12 pr-4 py-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                />
              </div>
              <div className="flex gap-4">
                <select
                  className="flex-1 p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                  value={selectedExperience}
                  onChange={(e) => setSelectedExperience(e.target.value)}
                >
                  {experienceRanges.map(range => (
                    <option key={range.value} value={range.value}>{range.label}</option>
                  ))}
                </select>
                <select
                  className="flex-1 p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                  value={selectedLanguage}
                  onChange={(e) => setSelectedLanguage(e.target.value)}
                >
                  {languages.map(lang => (
                    <option key={lang.value} value={lang.value}>{lang.label}</option>
                  ))}
                </select>
                <select
                  className="flex-1 p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                  value={selectedAvailability}
                  onChange={(e) => setSelectedAvailability(e.target.value)}
                >
                  {availability.map(avail => (
                    <option key={avail.value} value={avail.value}>{avail.label}</option>
                  ))}
                </select>
              </div>
            </div>
          </div>

          {/* Doctors Grid */}
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {filteredDoctors.map((doctor) => (
              <div
                key={doctor.id}
                className="bg-white rounded-xl shadow-sm overflow-hidden hover:shadow-md transition-all"
              >
                <div className="relative">
                  <img
                    src={doctor.image}
                    alt={doctor.name}
                    className="w-full h-64 object-cover"
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
                  {doctor.verificationBadge && (
                    <div className="absolute bottom-4 right-4 bg-blue-500 text-white px-2 py-1 rounded-full text-xs flex items-center gap-1">
                      <Award size={12} />
                      Verified
                    </div>
                  )}
                </div>

                <div className="p-6">
                  <div className="flex items-center justify-between mb-2">
                    <h3 className="text-xl font-semibold">{doctor.name}</h3>
                    <button className="text-red-500 hover:text-red-600">
                      <Heart size={20} />
                    </button>
                  </div>

                  <p className="text-blue-500 font-medium mb-1">{doctor.speciality}</p>
                  <p className="text-gray-600 text-sm mb-4">{doctor.qualification}</p>

                  <div className="grid grid-cols-2 gap-4 mb-4">
                    <div className="flex items-center gap-2">
                      <Star className="text-yellow-500" size={20} fill="currentColor" />
                      <div>
                        <div className="font-semibold">{doctor.rating}</div>
                        <div className="text-xs text-gray-500">Rating</div>
                      </div>
                    </div>
                    <div className="flex items-center gap-2">
                      <ThumbsUp className="text-blue-500" size={20} />
                      <div>
                        <div className="font-semibold">{(doctor.consultations / 1000).toFixed(1)}k+</div>
                        <div className="text-xs text-gray-500">Consultations</div>
                      </div>
                    </div>
                  </div>

                  <div className="space-y-2 mb-4">
                    <div className="flex items-center gap-2 text-gray-600">
                      <MapPin size={16} />
                      <span>{doctor.location}</span>
                    </div>
                    <div className="flex items-center gap-2 text-gray-600">
                      <Clock size={16} />
                      <span>{doctor.experience} experience</span>
                    </div>
                  </div>

                  <div className="flex flex-wrap gap-2 mb-4">
                    {doctor.specializations.map((spec, index) => (
                      <span
                        key={index}
                        className="px-2 py-1 bg-blue-50 text-blue-600 rounded-full text-xs"
                      >
                        {spec}
                      </span>
                    ))}
                  </div>

                  <div className="flex items-center justify-between text-sm mb-4">
                    <div className="flex items-center gap-2">
                      <Clock size={16} className="text-gray-400" />
                      <span>{doctor.waitTime}</span>
                    </div>
                    <span className="font-semibold">Fee: {doctor.consultationFee}</span>
                  </div>

                  <div className="grid grid-cols-2 gap-3">
                    <button
                      onClick={() => navigate(`/appointment/${type}/${specialty}/doctor/${doctor.id}`)}
                      className={`px-4 py-2 rounded-lg text-white flex items-center justify-center gap-2 ${
                        type === 'emergency' ? 'bg-red-500 hover:bg-red-600' : 'bg-blue-500 hover:bg-blue-600'
                      }`}
                    >
                      View Profile
                    </button>
                    <button
                      onClick={() => navigate(`/appointment/${type}/${specialty}/doctor/${doctor.id}/book`)}
                      className={`px-4 py-2 rounded-lg text-white flex items-center justify-center gap-2 ${
                        type === 'emergency' 
                          ? 'bg-red-500 hover:bg-red-600' 
                          : isOfflineConsultation
                          ? 'bg-green-500 hover:bg-green-600'
                          : 'bg-blue-500 hover:bg-blue-600'
                      }`}
                    >
                      {isOfflineConsultation ? (
                        <>
                          <Calendar size={16} />
                          Book Visit
                        </>
                      ) : (
                        <>
                          <Video size={16} />
                          Book Appointment
                        </>
                      )}
                    </button>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

export { DoctorListPage }