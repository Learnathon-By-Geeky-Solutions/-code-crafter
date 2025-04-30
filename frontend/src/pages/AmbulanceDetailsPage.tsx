import { ArrowLeft, MapPin, Phone, Mail, Star, Clock, Heart, Truck, Stethoscope, Shield, AlertTriangle, CheckCircle, Users, Siren, Thermometer, Clipboard, Navigation, Banknote } from 'lucide-react';
import { Link, useParams } from 'react-router-dom';
import { useState } from 'react';

interface AmbulanceFeature {
  icon: React.ReactNode;
  title: string;
  description: string;
}

export function AmbulanceDetailsPage() {
  const { type, id } = useParams<{ type: string; id: string }>();
  const [activeTab, setActiveTab] = useState<'overview' | 'features' | 'team' | 'reviews'>('overview');
  const [showCallModal, setShowCallModal] = useState(false);

  // This would come from an API in a real application
  const ambulanceDetails = {
    id: '1',
    name: 'Ad-Din Hospital Ambulance Service',
    image: 'https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Holding no-02, Road-Outer Circular Road, Bara Moghbazar, Dhaka',
    phone: '01652646814',
    email: 'adin@hospital.com',
    rating: 4.8,
    availability: 'available',
    features: [
      'Advanced Life Support',
      'Basic Life Support',
      'Patient Transport',
      'Emergency Response',
      'Trained Medical Staff',
      'Modern Equipment',
      'GPS Tracking',
      'Air Conditioning',
      'Oxygen Support',
      'Cardiac Monitor'
    ],
    operatingHours: '24/7',
    price: '৳ 2000/trip',
    responseTime: '10-15 mins',
    type: 'general',
    services: [
      'Patient Transport',
      'First Aid',
      'Emergency Response',
      'Medical Support',
      'Accident Response',
      'Inter-hospital Transfer'
    ],
    medicalTeam: {
      doctors: 1,
      nurses: 2,
      paramedics: 2
    },
    equipments: [
      'Stretcher',
      'Oxygen Cylinder',
      'First Aid Kit',
      'Defibrillator',
      'Suction Machine',
      'Ventilator',
      'ECG Monitor',
      'Blood Pressure Monitor'
    ],
    coordinates: {
      lat: 23.7461,
      lng: 90.3742
    },
    reviews: [
      {
        id: '1',
        user: 'Sarah Johnson',
        avatar: 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=150&h=150&q=80',
        rating: 5,
        comment: 'Excellent service! The ambulance arrived within 10 minutes of our call. The staff was professional and caring.',
        date: '2024-03-15'
      },
      {
        id: '2',
        user: 'Michael Chen',
        avatar: 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=150&h=150&q=80',
        rating: 4,
        comment: 'Good service overall. The ambulance was well-equipped and the paramedics were knowledgeable.',
        date: '2024-03-10'
      },
      {
        id: '3',
        user: 'Emily Rodriguez',
        avatar: 'https://images.unsplash.com/photo-1544005313-94ddf0286df2?auto=format&fit=crop&w=150&h=150&q=80',
        rating: 5,
        comment: 'Very prompt response during an emergency situation. The team was calm and professional.',
        date: '2024-03-05'
      }
    ],
    hospitalAffiliations: [
      'Ad-Din Medical College Hospital',
      'Ad-Din Women\'s Medical College Hospital',
      'Ad-Din Sakina Medical College Hospital'
    ],
    coverage: [
      'Dhaka City',
      'Gazipur',
      'Narayanganj',
      'Savar'
    ]
  };

  const features: AmbulanceFeature[] = [
    {
      icon: <Truck className="text-red-500" size={24} />,
      title: 'Modern Ambulance Fleet',
      description: 'Our ambulances are equipped with the latest medical technology and maintained to the highest standards.'
    },
    {
      icon: <Stethoscope className="text-blue-500" size={24} />,
      title: 'Professional Medical Team',
      description: 'Our team includes qualified doctors, nurses, and paramedics trained in emergency medical care.'
    },
    {
      icon: <Clock className="text-green-500" size={24} />,
      title: 'Quick Response Time',
      description: 'We pride ourselves on our rapid response times, ensuring help arrives when you need it most.'
    },
    {
      icon: <Shield className="text-purple-500" size={24} />,
      title: 'Safety Standards',
      description: 'All our ambulances and staff adhere to strict safety protocols and international standards.'
    },
    {
      icon: <Navigation className="text-orange-500" size={24} />,
      title: 'GPS Tracking',
      description: 'Real-time GPS tracking allows for efficient routing and accurate arrival time estimates.'
    },
    {
      icon: <Banknote className="text-emerald-500" size={24} />,
      title: 'Transparent Pricing',
      description: 'Clear, upfront pricing with no hidden fees or unexpected charges.'
    }
  ];

  return (
    <div className="min-h-screen bg-gradient-to-b from-red-50 to-white">
      {/* Hero Section */}
      <div className="relative h-[400px] overflow-hidden">
        <img
          src={ambulanceDetails.image}
          alt={ambulanceDetails.name}
          className="w-full h-full object-cover"
        />
        <div className="absolute inset-0 bg-gradient-to-r from-red-900/80 to-red-800/70">
          <div className="container mx-auto px-4 h-full flex items-center">
            <div className="max-w-3xl">
              <Link to={`/ambulance/book/${type}`} className="flex items-center text-white/80 hover:text-white mb-6 transition-colors">
                <ArrowLeft className="w-5 h-5 mr-2" />
                Back to Ambulance List
              </Link>
              <h1 className="text-4xl font-bold text-white mb-4">{ambulanceDetails.name}</h1>
              <div className="flex items-center gap-6 text-white/90 mb-6">
                <div className="flex items-center gap-2">
                  <Star className="text-yellow-400" size={20} fill="currentColor" />
                  <span className="text-lg">{ambulanceDetails.rating} Rating</span>
                </div>
                <div className="flex items-center gap-2">
                  <Clock size={20} />
                  <span className="text-lg">{ambulanceDetails.responseTime} Response</span>
                </div>
                <div className={`px-4 py-1 rounded-full text-sm ${
                  ambulanceDetails.availability === 'available'
                    ? 'bg-green-500 text-white'
                    : 'bg-red-500 text-white'
                }`}>
                  {ambulanceDetails.availability === 'available' ? 'Available Now' : 'Currently Busy'}
                </div>
              </div>
              <p className="text-xl text-white/90">
                Professional ambulance service with advanced medical support
              </p>
            </div>
          </div>
        </div>
      </div>

      <div className="container mx-auto px-4 -mt-16 relative z-10">
        {/* Quick Info Card */}
        <div className="bg-white rounded-2xl shadow-lg p-6 mb-8">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            <div className="flex items-center gap-4">
              <div className="bg-red-50 p-4 rounded-xl">
                <Truck className="text-red-500" size={24} />
              </div>
              <div>
                <h3 className="font-semibold">Ambulance Type</h3>
                <p className="text-gray-600">{ambulanceDetails.type.charAt(0).toUpperCase() + ambulanceDetails.type.slice(1)} Ambulance</p>
              </div>
            </div>
            <div className="flex items-center gap-4">
              <div className="bg-green-50 p-4 rounded-xl">
                <Banknote className="text-green-500" size={24} />
              </div>
              <div>
                <h3 className="font-semibold">Base Fare</h3>
                <p className="text-gray-600">{ambulanceDetails.price}</p>
              </div>
            </div>
            <div className="flex items-center gap-4">
              <div className="bg-blue-50 p-4 rounded-xl">
                <Users className="text-blue-500" size={24} />
              </div>
              <div>
                <h3 className="font-semibold">Medical Team</h3>
                <p className="text-gray-600">
                  {ambulanceDetails.medicalTeam.doctors} Doctor, {ambulanceDetails.medicalTeam.nurses} Nurses, {ambulanceDetails.medicalTeam.paramedics} Paramedics
                </p>
              </div>
            </div>
          </div>
        </div>

        {/* Main Content */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          {/* Left Column - Main Content */}
          <div className="md:col-span-2">
            {/* Tabs */}
            <div className="bg-white rounded-2xl shadow-lg overflow-hidden mb-8">
              <div className="flex border-b">
                <button
                  className={`flex-1 py-4 px-6 text-center font-medium ${
                    activeTab === 'overview'
                      ? 'text-red-500 border-b-2 border-red-500'
                      : 'text-gray-600 hover:text-red-500'
                  }`}
                  onClick={() => setActiveTab('overview')}
                >
                  Overview
                </button>
                <button
                  className={`flex-1 py-4 px-6 text-center font-medium ${
                    activeTab === 'features'
                      ? 'text-red-500 border-b-2 border-red-500'
                      : 'text-gray-600 hover:text-red-500'
                  }`}
                  onClick={() => setActiveTab('features')}
                >
                  Features
                </button>
                <button
                  className={`flex-1 py-4 px-6 text-center font-medium ${
                    activeTab === 'team'
                      ? 'text-red-500 border-b-2 border-red-500'
                      : 'text-gray-600 hover:text-red-500'
                  }`}
                  onClick={() => setActiveTab('team')}
                >
                  Medical Team
                </button>
                <button
                  className={`flex-1 py-4 px-6 text-center font-medium ${
                    activeTab === 'reviews'
                      ? 'text-red-500 border-b-2 border-red-500'
                      : 'text-gray-600 hover:text-red-500'
                  }`}
                  onClick={() => setActiveTab('reviews')}
                >
                  Reviews
                </button>
              </div>

              <div className="p-6">
                {/* Overview Tab */}
                {activeTab === 'overview' && (
                  <div>
                    <h2 className="text-xl font-semibold mb-4">About This Service</h2>
                    <p className="text-gray-700 mb-6">
                      Ad-Din Hospital Ambulance Service provides professional medical transport services with a focus on patient care and safety. Our ambulances are equipped with modern medical equipment and staffed by trained healthcare professionals to ensure the highest quality of care during transport.
                    </p>

                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
                      <div>
                        <h3 className="font-semibold mb-3">Services Offered</h3>
                        <ul className="space-y-2">
                          {ambulanceDetails.services.map((service, index) => (
                            <li key={index} className="flex items-center gap-2">
                              <CheckCircle size={16} className="text-green-500" />
                              <span>{service}</span>
                            </li>
                          ))}
                        </ul>
                      </div>
                      <div>
                        <h3 className="font-semibold mb-3">Coverage Areas</h3>
                        <ul className="space-y-2">
                          {ambulanceDetails.coverage.map((area, index) => (
                            <li key={index} className="flex items-center gap-2">
                              <MapPin size={16} className="text-blue-500" />
                              <span>{area}</span>
                            </li>
                          ))}
                        </ul>
                      </div>
                    </div>

                    <div>
                      <h3 className="font-semibold mb-3">Hospital Affiliations</h3>
                      <ul className="space-y-2">
                        {ambulanceDetails.hospitalAffiliations.map((hospital, index) => (
                          <li key={index} className="flex items-center gap-2">
                            <Heart size={16} className="text-red-500" />
                            <span>{hospital}</span>
                          </li>
                        ))}
                      </ul>
                    </div>
                  </div>
                )}

                {/* Features Tab */}
                {activeTab === 'features' && (
                  <div>
                    <h2 className="text-xl font-semibold mb-6">Key Features & Amenities</h2>
                    
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
                      {features.map((feature, index) => (
                        <div key={index} className="bg-gray-50 rounded-xl p-4">
                          <div className="flex items-start gap-4">
                            <div className="bg-white p-3 rounded-lg shadow-sm">
                              {feature.icon}
                            </div>
                            <div>
                              <h3 className="font-semibold mb-1">{feature.title}</h3>
                              <p className="text-sm text-gray-600">{feature.description}</p>
                            </div>
                          </div>
                        </div>
                      ))}
                    </div>

                    <div className="bg-red-50 rounded-xl p-6">
                      <h3 className="font-semibold mb-4 flex items-center gap-2">
                        <Clipboard className="text-red-500" />
                        Equipment Inventory
                      </h3>
                      <div className="grid grid-cols-2 md:grid-cols-3 gap-4">
                        {ambulanceDetails.equipments.map((equipment, index) => (
                          <div key={index} className="bg-white rounded-lg p-3 flex items-center gap-2">
                            <CheckCircle size={16} className="text-green-500" />
                            <span>{equipment}</span>
                          </div>
                        ))}
                      </div>
                    </div>
                  </div>
                )}

                {/* Team Tab */}
                {activeTab === 'team' && (
                  <div>
                    <h2 className="text-xl font-semibold mb-6">Medical Team</h2>
                    
                    <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
                      <div className="bg-blue-50 rounded-xl p-6 text-center">
                        <div className="bg-white w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4 shadow-sm">
                          <Stethoscope className="text-blue-500" size={32} />
                        </div>
                        <h3 className="text-2xl font-bold text-blue-600">{ambulanceDetails.medicalTeam.doctors}</h3>
                        <p className="text-gray-700">Doctor{ambulanceDetails.medicalTeam.doctors > 1 ? 's' : ''}</p>
                      </div>
                      <div className="bg-green-50 rounded-xl p-6 text-center">
                        <div className="bg-white w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4 shadow-sm">
                          <Heart className="text-green-500" size={32} />
                        </div>
                        <h3 className="text-2xl font-bold text-green-600">{ambulanceDetails.medicalTeam.nurses}</h3>
                        <p className="text-gray-700">Nurse{ambulanceDetails.medicalTeam.nurses > 1 ? 's' : ''}</p>
                      </div>
                      <div className="bg-purple-50 rounded-xl p-6 text-center">
                        <div className="bg-white w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4 shadow-sm">
                          <Users className="text-purple-500" size={32} />
                        </div>
                        <h3 className="text-2xl font-bold text-purple-600">{ambulanceDetails.medicalTeam.paramedics}</h3>
                        <p className="text-gray-700">Paramedic{ambulanceDetails.medicalTeam.paramedics > 1 ? 's' : ''}</p>
                      </div>
                    </div>

                    <div className="bg-gray-50 rounded-xl p-6">
                      <h3 className="font-semibold mb-4">Team Qualifications</h3>
                      <ul className="space-y-3">
                        <li className="flex items-start gap-3">
                          <CheckCircle className="text-green-500 mt-1" size={18} />
                          <div>
                            <p className="font-medium">Advanced Life Support (ALS) Certification</p>
                            <p className="text-sm text-gray-600">All medical team members are certified in Advanced Life Support techniques.</p>
                          </div>
                        </li>
                        <li className="flex items-start gap-3">
                          <CheckCircle className="text-green-500 mt-1" size={18} />
                          <div>
                            <p className="font-medium">Emergency Medical Technician (EMT) Training</p>
                            <p className="text-sm text-gray-600">Our paramedics are trained and certified as Emergency Medical Technicians.</p>
                          </div>
                        </li>
                        <li className="flex items-start gap-3">
                          <CheckCircle className="text-green-500 mt-1" size={18} />
                          <div>
                            <p className="font-medium">Trauma Care Specialization</p>
                            <p className="text-sm text-gray-600">Specialized training in handling trauma cases and emergency situations.</p>
                          </div>
                        </li>
                        <li className="flex items-start gap-3">
                          <CheckCircle className="text-green-500 mt-1" size={18} />
                          <div>
                            <p className="font-medium">Regular Training Updates</p>
                            <p className="text-sm text-gray-600">Continuous education and training to stay updated with the latest medical protocols.</p>
                          </div>
                        </li>
                      </ul>
                    </div>
                  </div>
                )}

                {/* Reviews Tab */}
                {activeTab === 'reviews' && (
                  <div>
                    <div className="flex items-center justify-between mb-6">
                      <h2 className="text-xl font-semibold">Customer Reviews</h2>
                      <div className="flex items-center gap-2 bg-yellow-50 px-3 py-1 rounded-full">
                        <Star className="text-yellow-500" size={18} fill="currentColor" />
                        <span className="font-semibold">{ambulanceDetails.rating} Rating</span>
                      </div>
                    </div>
                    
                    <div className="space-y-6">
                      {ambulanceDetails.reviews.map((review) => (
                        <div key={review.id} className="border-b pb-6 last:border-b-0 last:pb-0">
                          <div className="flex items-center gap-4 mb-3">
                            <img
                              src={review.avatar}
                              alt={review.user}
                              className="w-12 h-12 rounded-full object-cover"
                            />
                            <div>
                              <h3 className="font-medium">{review.user}</h3>
                              <p className="text-sm text-gray-500">{new Date(review.date).toLocaleDateString()}</p>
                            </div>
                            <div className="ml-auto flex">
                              {[...Array(5)].map((_, i) => (
                                <Star
                                  key={i}
                                  size={16}
                                  className={i < review.rating ? "text-yellow-500" : "text-gray-300"}
                                  fill={i < review.rating ? "currentColor" : "none"}
                                />
                              ))}
                            </div>
                          </div>
                          <p className="text-gray-700">{review.comment}</p>
                        </div>
                      ))}
                    </div>
                  </div>
                )}
              </div>
            </div>

            {/* Location Map */}
            <div className="bg-white rounded-2xl shadow-lg p-6 mb-8">
              <h2 className="text-xl font-semibold mb-4">Location</h2>
              <div className="bg-gray-100 rounded-xl h-64 flex items-center justify-center">
                <div className="text-center">
                  <MapPin className="mx-auto text-red-500 mb-2" size={32} />
                  <p className="text-gray-600">Interactive map will be displayed here</p>
                  <button className="mt-2 text-blue-500 hover:text-blue-600 font-medium">
                    Open in Google Maps
                  </button>
                </div>
              </div>
            </div>
          </div>

          {/* Right Column - Sidebar */}
          <div>
            {/* Contact Information */}
            <div className="bg-white rounded-2xl shadow-lg p-6 mb-8">
              <h2 className="text-xl font-semibold mb-4">Contact Information</h2>
              <div className="space-y-4">
                <div className="flex items-center gap-3">
                  <MapPin className="text-gray-400" size={20} />
                  <span>{ambulanceDetails.address}</span>
                </div>
                <div className="flex items-center gap-3">
                  <Phone className="text-gray-400" size={20} />
                  <span>{ambulanceDetails.phone}</span>
                </div>
                <div className="flex items-center gap-3">
                  <Mail className="text-gray-400" size={20} />
                  <span>{ambulanceDetails.email}</span>
                </div>
                <div className="flex items-center gap-3">
                  <Clock className="text-gray-400" size={20} />
                  <span>Operating Hours: {ambulanceDetails.operatingHours}</span>
                </div>
              </div>
              <button
                onClick={() => setShowCallModal(true)}
                className="w-full mt-6 py-3 bg-red-500 text-white rounded-xl hover:bg-red-600 transition-colors flex items-center justify-center gap-2"
              >
                <Phone size={20} />
                Call Now
              </button>
            </div>

            {/* Emergency Info */}
            <div className="bg-white rounded-2xl shadow-lg p-6 mb-8">
              <div className="flex items-center gap-3 mb-4">
                <div className="bg-red-100 p-3 rounded-full">
                  <Siren className="text-red-500" size={24} />
                </div>
                <h2 className="text-xl font-semibold">Emergency Info</h2>
              </div>
              <div className="space-y-4">
                <div className="flex items-center justify-between">
                  <span className="text-gray-600">Response Time</span>
                  <span className="font-semibold">{ambulanceDetails.responseTime}</span>
                </div>
                <div className="flex items-center justify-between">
                  <span className="text-gray-600">Base Fare</span>
                  <span className="font-semibold">{ambulanceDetails.price}</span>
                </div>
                <div className="flex items-center justify-between">
                  <span className="text-gray-600">Availability</span>
                  <span className={`px-3 py-1 rounded-full text-sm ${
                    ambulanceDetails.availability === 'available'
                      ? 'bg-green-100 text-green-600'
                      : 'bg-red-100 text-red-600'
                  }`}>
                    {ambulanceDetails.availability === 'available' ? 'Available' : 'Busy'}
                  </span>
                </div>
              </div>
              <div className="mt-6 pt-6 border-t">
                <div className="flex items-center gap-3 mb-4">
                  <AlertTriangle className="text-amber-500" size={20} />
                  <span className="text-gray-700 font-medium">Important Notes</span>
                </div>
                <ul className="space-y-2 text-sm text-gray-600">
                  <li className="flex items-start gap-2">
                    <CheckCircle size={16} className="text-green-500 mt-1" />
                    <span>Additional charges may apply for long-distance travel</span>
                  </li>
                  <li className="flex items-start gap-2">
                    <CheckCircle size={16} className="text-green-500 mt-1" />
                    <span>Payment can be made via cash, bKash, or Nagad</span>
                  </li>
                  <li className="flex items-start gap-2">
                    <CheckCircle size={16} className="text-green-500 mt-1" />
                    <span>Please have patient details ready when calling</span>
                  </li>
                </ul>
              </div>
            </div>

            {/* Ambulance Type Info */}
            <div className="bg-white rounded-2xl shadow-lg p-6">
              <div className="flex items-center gap-3 mb-4">
                {ambulanceDetails.type === 'general' ? (
                  <Truck className="text-blue-500" size={24} />
                ) : ambulanceDetails.type === 'icu' ? (
                  <Heart className="text-red-500" size={24} />
                ) : (
                  <Thermometer className="text-purple-500" size={24} />
                )}
                <h2 className="text-xl font-semibold">
                  {ambulanceDetails.type === 'general' ? 'General' : 
                   ambulanceDetails.type === 'icu' ? 'ICU' : 'Freezing'} Ambulance
                </h2>
              </div>
              <p className="text-gray-700 mb-4">
                {ambulanceDetails.type === 'general' 
                  ? 'General ambulances are equipped with basic life support equipment and trained staff for non-critical patient transport.'
                  : ambulanceDetails.type === 'icu'
                  ? 'ICU ambulances feature advanced life support equipment, ventilators, and specialized medical staff for critical care transport.'
                  : 'Freezing ambulances provide temperature-controlled environments for transporting organs, blood, and other temperature-sensitive medical items.'}
              </p>
              <div className="bg-gray-50 rounded-xl p-4">
                <h3 className="font-medium mb-3">Recommended For:</h3>
                <ul className="space-y-2">
                  {ambulanceDetails.type === 'general' ? (
                    <>
                      <li className="flex items-center gap-2 text-gray-600">
                        <CheckCircle size={16} className="text-green-500" />
                        <span>Non-critical patient transport</span>
                      </li>
                      <li className="flex items-center gap-2 text-gray-600">
                        <CheckCircle size={16} className="text-green-500" />
                        <span>Basic medical emergencies</span>
                      </li>
                      <li className="flex items-center gap-2 text-gray-600">
                        <CheckCircle size={16} className="text-green-500" />
                        <span>Hospital transfers</span>
                      </li>
                    </>
                  ) : ambulanceDetails.type === 'icu' ? (
                    <>
                      <li className="flex items-center gap-2 text-gray-600">
                        <CheckCircle size={16} className="text-green-500" />
                        <span>Critical care patients</span>
                      </li>
                      <li className="flex items-center gap-2 text-gray-600">
                        <CheckCircle size={16} className="text-green-500" />
                        <span>Ventilator-dependent patients</span>
                      </li>
                      <li className="flex items-center gap-2 text-gray-600">
                        <CheckCircle size={16} className="text-green-500" />
                        <span>Cardiac emergencies</span>
                      </li>
                    </>
                  ) : (
                    <>
                      <li className="flex items-center gap-2 text-gray-600">
                        <CheckCircle size={16} className="text-green-500" />
                        <span>Organ transport</span>
                      </li>
                      <li className="flex items-center gap-2 text-gray-600">
                        <CheckCircle size={16} className="text-green-500" />
                        <span>Blood and blood product transport</span>
                      </li>
                      <li className="flex items-center gap-2 text-gray-600">
                        <CheckCircle size={16} className="text-green-500" />
                        <span>Temperature-sensitive medication transport</span>
                      </li>
                    </>
                  )}
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Call Modal */}
      {showCallModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
          <div className="bg-white rounded-2xl p-6 max-w-md w-full">
            <div className="text-center mb-6">
              <div className="w-20 h-20 bg-red-100 rounded-full flex items-center justify-center mx-auto mb-4">
                <Phone className="text-red-500" size={32} />
              </div>
              <h3 className="text-xl font-bold mb-2">Call Ambulance Service</h3>
              <p className="text-gray-600">
                You are about to call {ambulanceDetails.name}. Please confirm to proceed.
              </p>
            </div>
            
            <div className="bg-gray-50 rounded-xl p-4 mb-6">
              <div className="flex items-center justify-between mb-2">
                <span className="text-gray-600">Phone Number:</span>
                <span className="font-semibold">{ambulanceDetails.phone}</span>
              </div>
              <div className="flex items-center justify-between">
                <span className="text-gray-600">Availability:</span>
                <span className={ambulanceDetails.availability === 'available' ? 'text-green-600' : 'text-red-600'}>
                  {ambulanceDetails.availability === 'available' ? 'Available Now' : 'Currently Busy'}
                </span>
              </div>
            </div>
            
            <div className="flex gap-4">
              <button
                onClick={() => setShowCallModal(false)}
                className="flex-1 py-3 bg-gray-100 text-gray-700 rounded-xl hover:bg-gray-200 transition-colors"
              >
                Cancel
              </button>
              <a
                href={`tel:${ambulanceDetails.phone}`}
                className="flex-1 py-3 bg-red-500 text-white rounded-xl hover:bg-red-600 transition-colors text-center"
              >
                Call Now
              </a>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}