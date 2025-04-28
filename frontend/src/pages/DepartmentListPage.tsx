import { ArrowLeft, Search, Heart, Brain, Stethoscope, Bone, Baby, Eye, Bluetooth as Tooth, Thermometer, Syringe, Activity, Droplets, Wind, LucideKey as Kidney, Droplet, Target, Sparkles, Users, Clock, Award } from 'lucide-react';
import { Link, useParams, useNavigate } from 'react-router-dom';
import { useState } from 'react';

interface Department {
  id: string;
  name: string;
  icon: React.ReactNode;
  description: string;
  doctorCount: number;
  waitTime: string;
  consultationFee: string;
  specializations: string[];
  facilities: string[];
  technologies: string[];
}

const departments: Department[] = [
  {
    id: 'cardiology',
    name: 'Cardiology',
    icon: <Heart className="w-12 h-12 text-red-500" />,
    description: 'Heart and cardiovascular system specialists',
    doctorCount: 45,
    waitTime: '10-15 mins',
    consultationFee: '৳1000',
    specializations: ['Interventional Cardiology', 'Heart Failure', 'Cardiac Rehabilitation'],
    facilities: ['Cath Lab', 'ECG', 'Echo'],
    technologies: ['3D Echo', 'Cardiac CT', 'Nuclear Cardiology']
  },
  {
    id: 'neurology',
    name: 'Neurology',
    icon: <Brain className="w-12 h-12 text-purple-500" />,
    description: 'Brain, spine, and nervous system specialists',
    doctorCount: 32,
    waitTime: '15-20 mins',
    consultationFee: '৳1200',
    specializations: ['Stroke', 'Epilepsy', 'Movement Disorders'],
    facilities: ['EEG Lab', 'EMG', 'Sleep Lab'],
    technologies: ['MRI', 'CT Scan', 'PET Scan']
  },
  {
    id: 'medicine',
    name: 'Medicine',
    icon: <Stethoscope className="w-12 h-12 text-blue-500" />,
    description: 'General medicine and internal medicine specialists',
    doctorCount: 58,
    waitTime: '5-10 mins',
    consultationFee: '৳800',
    specializations: ['Internal Medicine', 'Infectious Diseases', 'Preventive Care'],
    facilities: ['OPD', 'Emergency', 'ICU'],
    technologies: ['Digital X-Ray', 'Lab Services', 'Ultrasound']
  },
  {
    id: 'orthopedics',
    name: 'Orthopedics',
    icon: <Bone className="w-12 h-12 text-green-500" />,
    description: 'Bone and joint specialists',
    doctorCount: 28,
    waitTime: '20-25 mins',
    consultationFee: '৳1100',
    specializations: ['Joint Replacement', 'Spine Surgery', 'Sports Medicine'],
    facilities: ['Operation Theater', 'Physiotherapy', 'Rehabilitation'],
    technologies: ['Arthroscopy', 'Digital X-Ray', '3D CT Scan']
  },
  {
    id: 'pediatrics',
    name: 'Pediatrics',
    icon: <Baby className="w-12 h-12 text-pink-500" />,
    description: 'Child healthcare specialists',
    doctorCount: 42,
    waitTime: '10-15 mins',
    consultationFee: '৳900',
    specializations: ['Neonatology', 'Pediatric Surgery', 'Child Development'],
    facilities: ['NICU', 'Child Emergency', 'Vaccination'],
    technologies: ['Growth Monitoring', 'Development Assessment', 'Pediatric Imaging']
  },
  {
    id: 'ophthalmology',
    name: 'Ophthalmology',
    icon: <Eye className="w-12 h-12 text-amber-500" />,
    description: 'Eye care specialists',
    doctorCount: 25,
    waitTime: '25-30 mins',
    consultationFee: '৳1000',
    specializations: ['Cataract', 'Glaucoma', 'Retina'],
    facilities: ['Eye OT', 'Vision Lab', 'Laser Unit'],
    technologies: ['OCT', 'Visual Field', 'Fundus Camera']
  },
  {
    id: 'dental',
    name: 'Dental',
    icon: <Tooth className="w-12 h-12 text-cyan-500" />,
    description: 'Dental care specialists',
    doctorCount: 35,
    waitTime: '15-20 mins',
    consultationFee: '৳900',
    specializations: ['Orthodontics', 'Endodontics', 'Oral Surgery'],
    facilities: ['Dental OT', 'Implant Center', 'Dental Lab'],
    technologies: ['Digital X-Ray', '3D Scanning', 'CAD/CAM']
  },
  {
    id: 'fever',
    name: 'Fever & Flu',
    icon: <Thermometer className="w-12 h-12 text-orange-500" />,
    description: 'Fever, flu, and infectious disease specialists',
    doctorCount: 48,
    waitTime: '5-10 mins',
    consultationFee: '৳800',
    specializations: ['Infectious Diseases', 'Tropical Medicine', 'Vaccination'],
    facilities: ['Isolation Ward', 'Emergency Care', 'Lab Services'],
    technologies: ['Rapid Testing', 'PCR', 'Blood Analysis']
  }
];

export function DepartmentListPage() {
  const { hospitalId } = useParams<{ hospitalId: string }>();
  const navigate = useNavigate();
  const [searchTerm, setSearchTerm] = useState('');

  const filteredDepartments = departments.filter(dept =>
    dept.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
    dept.description.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="min-h-screen bg-gradient-to-b from-blue-50 to-white">
      {/* Hero Section */}
      <div className="relative h-[300px] overflow-hidden">
        <img
          src="https://images.unsplash.com/photo-1576091160550-2173dba999ef?auto=format&fit=crop&w=2000&q=80"
          alt="Hospital Departments"
          className="w-full h-full object-cover"
        />
        <div className="absolute inset-0 bg-gradient-to-r from-blue-900/90 to-blue-800/75">
          <div className="container mx-auto px-4 h-full flex items-center">
            <div className="max-w-2xl">
              <Link to="/hospitals" className="flex items-center text-white/80 hover:text-white mb-6">
                <ArrowLeft className="w-5 h-5 mr-2" />
                Back to Hospitals
              </Link>
              <h1 className="text-4xl font-bold text-white mb-4">Hospital Departments</h1>
              <p className="text-xl text-white/90">
                Choose from our specialized departments for expert medical care
              </p>
            </div>
          </div>
        </div>
      </div>

      <div className="container mx-auto px-4 -mt-16 relative z-10">
        {/* Search Bar */}
        <div className="bg-white rounded-2xl shadow-lg p-6 mb-12">
          <div className="relative">
            <Search className="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
            <input
              type="text"
              placeholder="Search departments by name or specialty..."
              className="w-full pl-12 pr-4 py-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          </div>
        </div>

        {/* Statistics */}
        <div className="grid grid-cols-2 md:grid-cols-4 gap-6 mb-12">
          <div className="bg-white rounded-xl p-6 shadow-sm">
            <div className="flex items-center gap-4">
              <div className="bg-blue-50 p-3 rounded-xl">
                <Users className="text-blue-500" size={24} />
              </div>
              <div>
                <div className="text-2xl font-bold text-blue-600">300+</div>
                <div className="text-gray-600">Doctors</div>
              </div>
            </div>
          </div>
          <div className="bg-white rounded-xl p-6 shadow-sm">
            <div className="flex items-center gap-4">
              <div className="bg-green-50 p-3 rounded-xl">
                <Stethoscope className="text-green-500" size={24} />
              </div>
              <div>
                <div className="text-2xl font-bold text-green-600">15+</div>
                <div className="text-gray-600">Specialties</div>
              </div>
            </div>
          </div>
          <div className="bg-white rounded-xl p-6 shadow-sm">
            <div className="flex items-center gap-4">
              <div className="bg-purple-50 p-3 rounded-xl">
                <Clock className="text-purple-500" size={24} />
              </div>
              <div>
                <div className="text-2xl font-bold text-purple-600">24/7</div>
                <div className="text-gray-600">Service</div>
              </div>
            </div>
          </div>
          <div className="bg-white rounded-xl p-6 shadow-sm">
            <div className="flex items-center gap-4">
              <div className="bg-red-50 p-3 rounded-xl">
                <Award className="text-red-500" size={24} />
              </div>
              <div>
                <div className="text-2xl font-bold text-red-600">50+</div>
                <div className="text-gray-600">Awards</div>
              </div>
            </div>
          </div>
        </div>

        {/* Departments Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {filteredDepartments.map((department) => (
            <div
              key={department.id}
              className="bg-white rounded-xl shadow-sm hover:shadow-md transition-all cursor-pointer group"
              onClick={() => navigate(`/hospitals/${hospitalId}/departments/${department.id}/doctors`)}
            >
              <div className="p-6">
                <div className="flex items-center gap-4 mb-6">
                  <div className="bg-gray-50 p-4 rounded-xl group-hover:scale-110 transition-transform">
                    {department.icon}
                  </div>
                  <div>
                    <h3 className="text-xl font-semibold mb-1">{department.name}</h3>
                    <p className="text-gray-600">{department.description}</p>
                  </div>
                </div>

                <div className="grid grid-cols-3 gap-4 mb-6">
                  <div className="text-center p-3 bg-gray-50 rounded-lg">
                    <div className="font-semibold text-blue-600">{department.doctorCount}</div>
                    <div className="text-sm text-gray-600">Doctors</div>
                  </div>
                  <div className="text-center p-3 bg-gray-50 rounded-lg">
                    <div className="font-semibold text-green-600">{department.waitTime}</div>
                    <div className="text-sm text-gray-600">Wait Time</div>
                  </div>
                  <div className="text-center p-3 bg-gray-50 rounded-lg">
                    <div className="font-semibold text-purple-600">{department.consultationFee}</div>
                    <div className="text-sm text-gray-600">Starting Fee</div>
                  </div>
                </div>

                <div className="space-y-4">
                  <div>
                    <h4 className="text-sm font-medium text-gray-700 mb-2">Key Specializations</h4>
                    <div className="flex flex-wrap gap-2">
                      {department.specializations.map((spec, index) => (
                        <span
                          key={index}
                          className="px-3 py-1 bg-blue-50 text-blue-600 rounded-full text-sm"
                        >
                          {spec}
                        </span>
                      ))}
                    </div>
                  </div>

                  <div>
                    <h4 className="text-sm font-medium text-gray-700 mb-2">Facilities</h4>
                    <div className="flex flex-wrap gap-2">
                      {department.facilities.map((facility, index) => (
                        <span
                          key={index}
                          className="px-3 py-1 bg-green-50 text-green-600 rounded-full text-sm"
                        >
                          {facility}
                        </span>
                      ))}
                    </div>
                  </div>

                  <div>
                    <h4 className="text-sm font-medium text-gray-700 mb-2">Technologies</h4>
                    <div className="flex flex-wrap gap-2">
                      {department.technologies.map((tech, index) => (
                        <span
                          key={index}
                          className="px-3 py-1 bg-purple-50 text-purple-600 rounded-full text-sm"
                        >
                          {tech}
                        </span>
                      ))}
                    </div>
                  </div>
                </div>

                <button className="w-full mt-6 py-3 bg-blue-500 text-white rounded-xl hover:bg-blue-600 transition-colors flex items-center justify-center gap-2">
                  View Doctors
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}