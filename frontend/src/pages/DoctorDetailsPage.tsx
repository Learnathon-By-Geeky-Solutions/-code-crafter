import { ArrowLeft, Star, Clock, MapPin, Award, ThumbsUp, Video, Calendar, Heart, Phone, Mail, Globe, CheckCircle, AlertTriangle } from 'lucide-react';
import { Link, useParams, useNavigate, useLocation } from 'react-router-dom';

interface TimeSlot {
  day: string;
  time: string;
}

const consultationTimes: TimeSlot[] = [
  { day: 'Saturday', time: '10:00 PM to 11:40 PM' },
  { day: 'Sunday', time: '10:00 PM to 11:40 PM' },
  { day: 'Monday', time: '10:00 PM to 11:40 PM' },
  { day: 'Tuesday', time: '10:00 PM to 11:40 PM' },
  { day: 'Wednesday', time: '10:00 PM to 11:40 PM' },
  { day: 'Thursday', time: '10:00 PM to 11:40 PM' },
  { day: 'Friday', time: '10:00 PM to 11:40 PM' },
];

export default function DoctorDetailsPage() {
  const location = useLocation();
  const { hospitalId, departmentId, type, specialty, id } = useParams();
  const navigate = useNavigate();
  const isOfflineConsultation = location.pathname.includes('/hospitals/');

  const handleBooking = (bookingType: 'video' | 'hospital') => {
    const basePath = isOfflineConsultation
      ? `/hospitals/${hospitalId}/departments/${departmentId}/doctor/${id}`
      : `/appointment/${type}/${specialty}/doctor/${id}`;

    navigate(`${basePath}/book`);
  };

  const doctor = {
    name: "Dr. Sarah Johnson",
    speciality: "Cardiologist",
    image: "https://images.unsplash.com/photo-1559839734-2b71ea197ec2?auto=format&fit=crop&w=300&h=300",
    rating: 4.9,
    consultations: 15000,
    languages: ["English", "Bengali", "Hindi"],
    about: "Dr. Sarah Johnson is a board-certified cardiologist with over 15 years of experience in treating complex cardiac conditions. She specializes in interventional cardiology and has performed over 5,000 successful cardiac procedures.\n\nAreas of Expertise:\n• Interventional Cardiology\n• Heart Failure Management\n• Cardiac Rehabilitation\n• Preventive Cardiology\n\nPatient Care Philosophy:\n• Personalized treatment plans\n• Evidence-based practices\n• Continuous monitoring\n• Patient education focus",
    education: [
      {
        degree: "MD in Cardiology",
        institution: "Johns Hopkins University",
        year: "2005"
      },
      {
        degree: "Residency in Internal Medicine",
        institution: "Mayo Clinic",
        year: "2008"
      }
    ],
    experience_history: [
      {
        position: "Senior Cardiologist",
        hospital: "City Heart Center",
        duration: "2015 - Present"
      },
      {
        position: "Consultant Cardiologist",
        hospital: "Metropolitan Hospital",
        duration: "2008 - 2015"
      }
    ],
    awards: [
      "Best Cardiologist Award 2023",
      "Excellence in Cardiac Care 2022",
      "Research Pioneer Award 2021"
    ],
    publications: [
      "Advanced Techniques in Interventional Cardiology",
      "Modern Approaches to Heart Failure Management",
      "Preventive Cardiology: A Comprehensive Guide"
    ],
    specializations: [
      "Interventional Cardiology",
      "Heart Failure Management",
      "Cardiac Rehabilitation",
      "Preventive Cardiology",
      "Echocardiography"
    ],
    services: [
      "Cardiac Consultation",
      "ECG",
      "Echocardiogram",
      "Stress Test",
      "Holter Monitoring"
    ],
    location: "123 Medical Center Drive, Dhaka",
    phone: "+880 1234-567890",
    email: "dr.sarah@cityheart.com",
    website: "www.drsarahjohnson.com",
    consultationFee: "৳1000",
    followUpFee: "৳800",
    availability: "Available Today",
    nextAvailable: "2:30 PM",
    emergencyAvailable: true
  };

  return (
    <div className="min-h-screen bg-gradient-to-b from-gray-50 to-white">
      {/* Hero Section */}
      <div className="relative h-[400px] overflow-hidden">
        {/* Background Pattern */}
        <div className="absolute inset-0 bg-[#2B3B87]">
          <div className="absolute inset-0" style={{
            backgroundImage: `url("data:image/svg+xml,%3Csvg width='60' height='60' viewBox='0 0 60 60' xmlns='http://www.w3.org/2000/svg'%3E%3Cg fill='none' fill-rule='evenodd'%3E%3Cg fill='%23ffffff' fill-opacity='0.05'%3E%3Cpath d='M36 34v-4h-2v4h-4v2h4v4h2v-4h4v-2h-4zm0-30V0h-2v4h-4v2h4v4h2V6h4V4h-4zM6 34v-4H4v4H0v2h4v4h2v-4h4v-2H6zM6 4V0H4v4H0v2h4v4h2V6h4V4H6z'/%3E%3C/g%3E%3C/g%3E%3C/svg%3E")`,
          }} />
          <div className="absolute inset-0 bg-gradient-to-r from-blue-900/95 to-blue-800/95" />
        </div>

        <div className="container mx-auto px-4 h-full relative">
          <div className="flex flex-col justify-center h-full">
            <Link 
              to={isOfflineConsultation 
                ? `/hospitals/${hospitalId}/departments/${departmentId}/doctors`
                : `/appointment/${type}/${specialty}/doctors`
              } 
              className="flex items-center text-white/80 hover:text-white mb-8 transition-colors"
            >
              <ArrowLeft className="w-5 h-5 mr-2" />
              Back
            </Link>
            <div className="flex items-center gap-8">
              <div className="relative">
                <img
                  src={doctor.image}
                  alt={doctor.name}
                  className="w-40 h-40 rounded-2xl object-cover shadow-xl"
                />
                <div className="absolute -bottom-3 -right-3 bg-green-500 text-white px-4 py-1 rounded-full text-sm shadow-lg">
                  Available
                </div>
              </div>
              <div>
                <h1 className="text-4xl font-bold text-white mb-2">{doctor.name}</h1>
                <p className="text-2xl text-white/90 mb-4">{doctor.speciality}</p>
                <div className="flex items-center gap-6">
                  <div className="flex items-center gap-2 text-white bg-white/10 px-4 py-2 rounded-full backdrop-blur-sm">
                    <Star className="text-yellow-400" size={24} fill="currentColor" />
                    <span className="text-lg">{doctor.rating} Rating</span>
                  </div>
                  <div className="flex items-center gap-2 text-white bg-white/10 px-4 py-2 rounded-full backdrop-blur-sm">
                    <ThumbsUp size={24} />
                    <span className="text-lg">{(doctor.consultations / 1000).toFixed(1)}k+ Consultations</span>
                  </div>
                </div>
                <div className="flex gap-3 mt-6">
                  {doctor.languages.map((lang, index) => (
                    <span key={index} className="text-white/80 bg-white/5 px-3 py-1 rounded-full text-sm backdrop-blur-sm">
                      {lang}
                    </span>
                  ))}
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Decorative Elements */}
        <div className="absolute bottom-0 left-0 right-0 h-24 bg-gradient-to-t from-gray-50 to-transparent" />
        <div className="absolute -bottom-12 left-1/2 transform -translate-x-1/2 w-full max-w-4xl">
          <div className="bg-white rounded-2xl shadow-xl p-6 grid grid-cols-3 gap-6">
            <div className="text-center">
              <div className="text-3xl font-bold text-blue-600 mb-1">{doctor.consultationFee}</div>
              <div className="text-gray-600">Consultation Fee</div>
            </div>
            <div className="text-center border-x border-gray-100">
              <div className="text-3xl font-bold text-green-600 mb-1">{doctor.nextAvailable}</div>
              <div className="text-gray-600">Next Available</div>
            </div>
            <div className="text-center">
              <div className="text-3xl font-bold text-purple-600 mb-1">24/7</div>
              <div className="text-gray-600">Emergency Available</div>
            </div>
          </div>
        </div>
      </div>

      <div className="container mx-auto px-4 py-12">
        <div className="max-w-4xl mx-auto">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            {/* Left Column */}
            <div className="md:col-span-2 space-y-6">
              {/* About Section */}
              <div className="bg-white rounded-xl shadow-sm p-6">
                <h2 className="text-xl font-semibold mb-4">About</h2>
                <div className="prose max-w-none">
                  {doctor.about.split('\n\n').map((paragraph, index) => {
                    if (paragraph.includes('•')) {
                      const [title, ...bullets] = paragraph.split('\n');
                      return (
                        <div key={index} className="mb-4">
                          <p className="mb-2">{title}</p>
                          <ul className="list-none space-y-1">
                            {bullets.map((bullet, i) => (
                              <li key={i} className="flex items-center gap-2">
                                <CheckCircle size={16} className="text-green-500 flex-shrink-0" />
                                <span>{bullet.replace('•', '').trim()}</span>
                              </li>
                            ))}
                          </ul>
                        </div>
                      );
                    }
                    return <p key={index} className="mb-4">{paragraph}</p>;
                  })}
                </div>
              </div>

              {/* Education & Experience */}
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div className="bg-white rounded-xl shadow-sm p-6">
                  <h2 className="text-xl font-semibold mb-4">Education</h2>
                  <div className="space-y-4">
                    {doctor.education.map((edu, index) => (
                      <div key={index} className="flex gap-4">
                        <div className="w-2 h-2 mt-2 rounded-full bg-blue-500"></div>
                        <div>
                          <h3 className="font-medium">{edu.degree}</h3>
                          <p className="text-sm text-gray-600">{edu.institution}</p>
                          <p className="text-sm text-gray-500">{edu.year}</p>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>

                <div className="bg-white rounded-xl shadow-sm p-6">
                  <h2 className="text-xl font-semibold mb-4">Experience</h2>
                  <div className="space-y-4">
                    {doctor.experience_history.map((exp, index) => (
                      <div key={index} className="flex gap-4">
                        <div className="w-2 h-2 mt-2 rounded-full bg-green-500"></div>
                        <div>
                          <h3 className="font-medium">{exp.position}</h3>
                          <p className="text-sm text-gray-600">{exp.hospital}</p>
                          <p className="text-sm text-gray-500">{exp.duration}</p>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>
              </div>

              {/* Awards & Publications */}
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div className="bg-white rounded-xl shadow-sm p-6">
                  <h2 className="text-xl font-semibold mb-4">Awards</h2>
                  <div className="space-y-3">
                    {doctor.awards.map((award, index) => (
                      <div key={index} className="flex items-center gap-2">
                        <Award size={16} className="text-yellow-500" />
                        <span>{award}</span>
                      </div>
                    ))}
                  </div>
                </div>

                <div className="bg-white rounded-xl shadow-sm p-6">
                  <h2 className="text-xl font-semibold mb-4">Publications</h2>
                  <div className="space-y-3">
                    {doctor.publications.map((pub, index) => (
                      <div key={index} className="flex items-center gap-2">
                        <Globe size={16} className="text-blue-500" />
                        <span>{pub}</span>
                      </div>
                    ))}
                  </div>
                </div>
              </div>

              {/* Specializations & Services */}
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div className="bg-white rounded-xl shadow-sm p-6">
                  <h2 className="text-xl font-semibold mb-4">Specializations</h2>
                  <div className="flex flex-wrap gap-2">
                    {doctor.specializations.map((spec, index) => (
                      <span
                        key={index}
                        className="px-3 py-1 bg-blue-50 text-blue-600 rounded-full text-sm"
                      >
                        {spec}
                      </span>
                    ))}
                  </div>
                </div>

                <div className="bg-white rounded-xl shadow-sm p-6">
                  <h2 className="text-xl font-semibold mb-4">Services</h2>
                  <div className="space-y-2">
                    {doctor.services.map((service, index) => (
                      <div key={index} className="flex items-center gap-2">
                        <CheckCircle size={16} className="text-green-500" />
                        <span>{service}</span>
                      </div>
                    ))}
                  </div>
                </div>
              </div>
            </div>

            {/* Right Column - Sidebar */}
            <div className="space-y-6">
              {/* Contact Info */}
              <div className="bg-white rounded-xl shadow-sm p-6">
                <h2 className="text-xl font-semibold mb-4">Contact Information</h2>
                <div className="space-y-4">
                  <div className="flex items-center gap-3">
                    <MapPin className="text-gray-400" size={20} />
                    <span>{doctor.location}</span>
                  </div>
                  <div className="flex items-center gap-3">
                    <Phone className="text-gray-400" size={20} />
                    <span>{doctor.phone}</span>
                  </div>
                  <div className="flex items-center gap-3">
                    <Mail className="text-gray-400" size={20} />
                    <span>{doctor.email}</span>
                  </div>
                  <div className="flex items-center gap-3">
                    <Globe className="text-gray-400" size={20} />
                    <span>{doctor.website}</span>
                  </div>
                </div>
              </div>

              {/* Consultation Fees */}
              <div className="bg-white rounded-xl shadow-sm p-6">
                <h2 className="text-xl font-semibold mb-4">Consultation Fees</h2>
                <div className="space-y-4">
                  <div className="flex justify-between items-center">
                    <span>First Visit</span>
                    <span className="font-semibold">{doctor.consultationFee}</span>
                  </div>
                  <div className="flex justify-between items-center">
                    <span>Follow Up</span>
                    <span className="font-semibold">{doctor.followUpFee}</span>
                  </div>
                </div>
              </div>

              {/* Availability */}
              <div className="bg-white rounded-xl shadow-sm p-6">
                <div className="flex items-center justify-between mb-4">
                  <h2 className="text-xl font-semibold">Availability</h2>
                  <span className={`px-3 py-1 rounded-full text-sm ${
                    doctor.availability.includes('Available')
                      ? 'bg-green-100 text-green-600'
                      : 'bg-yellow-100 text-yellow-600'
                  }`}>
                    {doctor.availability}
                  </span>
                </div>
                <div className="space-y-4">
                  <div className="flex items-center justify-between text-sm">
                    <span>Next Available at</span>
                    <span className="font-semibold">{doctor.nextAvailable}</span>
                  </div>
                  {doctor.emergencyAvailable && (
                    <div className="flex items-center gap-2 text-red-500">
                      <AlertTriangle size={16} />
                      <span>Available for Emergency</span>
                    </div>
                  )}
                </div>
              </div>

              {/* Booking Buttons */}
              <div className="space-y-3">
                {!isOfflineConsultation && (
                  <button
                    onClick={() => handleBooking('video')}
                    className="w-full py-3 bg-blue-500 text-white rounded-full hover:bg-blue-600 transition-colors flex items-center justify-center gap-2"
                  >
                    <Video size={20} />
                    Book Video Consultation
                  </button>
                )}
                <button
                  onClick={() => handleBooking('hospital')}
                  className="w-full py-3 bg-green-500 text-white rounded-full hover:bg-green-600 transition-colors flex items-center justify-center gap-2"
                >
                  <Calendar size={20} />
                  Book Hospital Visit
                </button>
              </div>

              {/* Consultation Schedule */}
              <div className="bg-white rounded-xl shadow-sm p-6">
                <h2 className="text-xl font-semibold mb-4">Consultation Hours</h2>
                <div className="space-y-3">
                  {consultationTimes.map((slot, index) => (
                    <div key={index} className="flex justify-between items-center text-sm bg-gray-50 p-3 rounded-lg">
                      <span className="font-medium">{slot.day}</span>
                      <span className="text-gray-600">{slot.time}</span>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export { DoctorDetailsPage }