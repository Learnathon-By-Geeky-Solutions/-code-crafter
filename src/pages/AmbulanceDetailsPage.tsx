import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import {
  ArrowLeft, MapPin, Phone, Mail, Star, Clock, Heart, Truck, Stethoscope,
  Shield, AlertTriangle, CheckCircle, Users, Siren, Thermometer, Clipboard,
  Navigation, Banknote
} from 'lucide-react';
import { api } from '../lib/api'; 



interface Ambulance {
  id: number;
  user: {
    fname: string;
    lname: string;
    phone: string;
    email: string;
    area: string;
    upazila: {
      name: string;
      district: {
        name: string;
        division: {
          name: string;
        };
      };
    };
  };
  ambulanceType: string;
  ambulanceNumber: string;
  ambulanceStatus: string;
  about: string;
  serviceOffers: string[];
  hospitalAffiliation: string;
  coverageAreas: string[];
  responseTime: number;
  doctors: number;
  nurses: number;
  paramedics: number;
  teamQualification: string[];
  startingFee: number;
  averageRating: number;
  totalReviews: number;
}

interface AmbulanceFeature {
  icon: React.ReactNode;
  title: string;
  description: string;
}

export function AmbulanceDetailsPage() {
  const { type, id } = useParams<{ type: string; id: string }>();
  const [ambulance, setAmbulance] = useState<Ambulance | null>(null);
  const [loading, setLoading] = useState(true);
  const [activeTab, setActiveTab] = useState<'overview' | 'features' | 'team' | 'reviews'>('overview');
  const [showCallModal, setShowCallModal] = useState(false);
  const [showBookingModal, setShowBookingModal] = useState(false);
  const [bookingSuccess, setBookingSuccess] = useState(false);
  const [bookingError, setBookingError] = useState(false);


  useEffect(() => {
    if (id) fetchAmbulanceDetails(id);
  }, [id]);

  const fetchAmbulanceDetails = async (ambulanceId: string) => {
    try {
      setLoading(true);
      const { data: baseResponse } = await api.get(`/api/v1/ambulance/${ambulanceId}`);
      if (baseResponse.code === 'XS0001') {
        setAmbulance(baseResponse.data);
      }
    } catch (error) {
      console.error('Failed to fetch ambulance details', error);
    } finally {
      setLoading(false);
    }
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

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="text-gray-400">Loading Ambulance Details...</div>
      </div>
    );
  }

  if (!ambulance) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="text-red-500">Ambulance not found!</div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-b from-red-50 to-white">
      {/* Hero Section */}
      <div className="relative h-[400px] overflow-hidden">
        <img
          src="https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=800&h=400&q=80"
          alt="Ambulance"
          className="w-full h-full object-cover"
        />
        <div className="absolute inset-0 bg-gradient-to-r from-red-900/80 to-red-800/70">
          <div className="container mx-auto px-4 h-full flex items-center">
            <div className="max-w-3xl">
              <Link to={`/ambulance/book/${type}`} className="flex items-center text-white/80 hover:text-white mb-6 transition-colors">
                <ArrowLeft className="w-5 h-5 mr-2" />
                Back to Ambulance List
              </Link>
              <h1 className="text-4xl font-bold text-white mb-4">{ambulance.user.fname} {ambulance.user.lname}</h1>
              <div className="flex items-center gap-6 text-white/90 mb-6">
                <div className="flex items-center gap-2">
                  <Star className="text-yellow-400" size={20} fill="currentColor" />
                  <span className="text-lg">{ambulance.averageRating} Rating</span>
                </div>
                <div className="flex items-center gap-2">
                  <Clock size={20} />
                  <span className="text-lg">{ambulance.responseTime} min Response</span>
                </div>
                <div className={`px-4 py-1 rounded-full text-sm ${ambulance.ambulanceStatus === 'AVAILABLE' ? 'bg-green-500' : 'bg-red-500'} text-white`}>
                  {ambulance.ambulanceStatus === 'AVAILABLE' ? 'Available Now' : 'Currently Busy'}
                </div>
              </div>
              <p className="text-xl text-white/90">{ambulance.about}</p>
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
          <p className="text-gray-600 capitalize">{ambulance.ambulanceType} Ambulance</p>
        </div>
      </div>
      <div className="flex items-center gap-4">
        <div className="bg-green-50 p-4 rounded-xl">
          <Banknote className="text-green-500" size={24} />
        </div>
        <div>
          <h3 className="font-semibold">Base Fare</h3>
          <p className="text-gray-600">৳ {ambulance.startingFee}</p>
        </div>
      </div>
      <div className="flex items-center gap-4">
        <div className="bg-blue-50 p-4 rounded-xl">
          <Users className="text-blue-500" size={24} />
        </div>
        <div>
          <h3 className="font-semibold">Medical Team</h3>
          <p className="text-gray-600">{ambulance.doctors} Doctor, {ambulance.nurses} Nurses, {ambulance.paramedics} Paramedics</p>
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
          {['overview', 'features', 'team', 'reviews'].map(tab => (
            <button
              key={tab}
              className={`flex-1 py-4 px-6 text-center font-medium ${
                activeTab === tab ? 'text-red-500 border-b-2 border-red-500' : 'text-gray-600 hover:text-red-500'
              }`}
              onClick={() => setActiveTab(tab as typeof activeTab)}
            >
              {tab.charAt(0).toUpperCase() + tab.slice(1)}
            </button>
          ))}
        </div>

        <div className="p-6">
          {/* Overview Tab */}
          {activeTab === 'overview' && (
            <div>
              <h2 className="text-xl font-semibold mb-4">About This Service</h2>
              <p className="text-gray-700 mb-6">{ambulance.about}</p>

              <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
                <div>
                  <h3 className="font-semibold mb-3">Services Offered</h3>
                  <ul className="space-y-2">
                    {ambulance.serviceOffers.map((service, idx) => (
                      <li key={idx} className="flex items-center gap-2">
                        <CheckCircle size={16} className="text-green-500" />
                        <span>{service}</span>
                      </li>
                    ))}
                  </ul>
                </div>
                <div>
                  <h3 className="font-semibold mb-3">Coverage Areas</h3>
                  <ul className="space-y-2">
                    {ambulance.coverageAreas.map((area, idx) => (
                      <li key={idx} className="flex items-center gap-2">
                        <MapPin size={16} className="text-blue-500" />
                        <span>{area}</span>
                      </li>
                    ))}
                  </ul>
                </div>
              </div>

              <div>
                <h3 className="font-semibold mb-3">Hospital Affiliation</h3>
                <div className="flex items-center gap-2">
                  <Heart size={16} className="text-red-500" />
                  <span>{ambulance.hospitalAffiliation}</span>
                </div>
              </div>
            </div>
          )}

          {/* Features Tab */}
          {activeTab === 'features' && (
            <div>
              <h2 className="text-xl font-semibold mb-6">Key Features & Amenities</h2>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
                {features.map((feature, idx) => (
                  <div key={idx} className="bg-gray-50 rounded-xl p-4 flex items-start gap-4">
                    <div className="bg-white p-3 rounded-lg shadow-sm">
                      {feature.icon}
                    </div>
                    <div>
                      <h3 className="font-semibold">{feature.title}</h3>
                      <p className="text-sm text-gray-600">{feature.description}</p>
                    </div>
                  </div>
                ))}
              </div>
              <div className="bg-red-50 rounded-xl p-6">
                <h3 className="font-semibold mb-4 flex items-center gap-2">
                  <Clipboard className="text-red-500" />
                  Equipment (Not Provided from API)
                </h3>
                <p className="text-gray-500">Equipment list is static or can be hardcoded if needed.</p>
              </div>
            </div>
          )}

          {/* Medical Team Tab */}
          {activeTab === 'team' && (
            <div>
              <h2 className="text-xl font-semibold mb-6">Medical Team</h2>
              <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                {[
                  { count: ambulance.doctors, label: 'Doctor', icon: <Stethoscope className="text-blue-500" size={32} /> },
                  { count: ambulance.nurses, label: 'Nurse', icon: <Heart className="text-green-500" size={32} /> },
                  { count: ambulance.paramedics, label: 'Paramedic', icon: <Users className="text-purple-500" size={32} /> },
                ].map((team, idx) => (
                  <div key={idx} className="bg-gray-50 rounded-xl p-6 text-center">
                    <div className="bg-white w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4 shadow-sm">
                      {team.icon}
                    </div>
                    <h3 className="text-2xl font-bold">{team.count}</h3>
                    <p className="text-gray-600">{team.label}{team.count !== 1 && 's'}</p>
                  </div>
                ))}
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
               <span className="font-semibold">{ambulance.averageRating || 0} Rating</span>
             </div>
           </div>
         
           {ambulance.totalReviews > 0 && (ambulance as any).reviews?.length > 0 ? (
             <div className="space-y-6">
               {(ambulance as any).reviews.map((review: any) => (
                 <div key={review.id} className="border-b pb-6 last:border-b-0 last:pb-0">
                   <div className="flex items-center gap-4 mb-3">
                     <img
                       src={review.avatar || "https://i.pravatar.cc/100"} 
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
           ) : (
             <p className="text-gray-400 text-center">No reviews available yet.</p>
           )}
         </div>
         
          )}
        </div>
      </div>

      {/* Location Map */}
      <div className="bg-white rounded-2xl shadow-lg p-6 mb-8">
        <h2 className="text-xl font-semibold mb-4">Location</h2>
        <div className="bg-gray-100 rounded-xl h-64 flex items-center justify-center">
          <MapPin className="text-red-500" size={32} />
          <span className="text-gray-500 ml-2">{ambulance.user.area}, {ambulance.user.upazila.name}</span>
        </div>
      </div>
    </div>

    {/* Sidebar */}
    <div className="space-y-6">
      {/* Contact Info */}
      <div className="bg-white rounded-2xl shadow-lg p-6">
        <h2 className="text-xl font-semibold mb-4">Contact Information</h2>
        <div className="space-y-3">
          <div className="flex items-center gap-2 text-gray-700">
            <MapPin size={18} />
            <span>{ambulance.user.area}, {ambulance.user.upazila.name}</span>
          </div>
          <div className="flex items-center gap-2 text-gray-700">
            <Phone size={18} />
            <span>{ambulance.user.phone}</span>
          </div>
          <div className="flex items-center gap-2 text-gray-700">
            <Mail size={18} />
            <span>{ambulance.user.email}</span>
          </div>
          <div className="flex items-center gap-2 text-gray-700">
            <Clock size={18} />
            <span>24/7 Available</span>
          </div>
        </div>
        <button
        onClick={() => setShowCallModal(true)}
        className="w-full mt-6 py-3 bg-red-500 text-white rounded-xl hover:bg-red-600 flex items-center justify-center gap-2"
      >
        <Phone size={20} />
        Call Now
      </button>

      {/* Book Now Button */}
      <button
        onClick={() => setShowBookingModal(true)}
        className="w-full mt-3 py-3 bg-green-500 text-white rounded-xl hover:bg-green-600 flex items-center justify-center gap-2"
      >
        🚑 Book Now
      </button>

      </div>

      {/* Emergency Info */}
      <div className="bg-white rounded-2xl shadow-lg p-6">
        <h2 className="text-xl font-semibold mb-4 flex items-center gap-2">
          <Siren className="text-red-500" size={24} /> Emergency Info
        </h2>
        <div className="space-y-2 text-sm text-gray-600">
          <div className="flex justify-between">
            <span>Response Time:</span> <span>{ambulance.responseTime} mins</span>
          </div>
          <div className="flex justify-between">
            <span>Base Fare:</span> <span>৳ {ambulance.startingFee}</span>
          </div>
          <div className="flex justify-between">
            <span>Availability:</span> 
            <span className={ambulance.ambulanceStatus === 'AVAILABLE' ? 'text-green-500' : 'text-red-500'}>
              {ambulance.ambulanceStatus}
            </span>
          </div>
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
        <Phone className="mx-auto text-red-500 mb-4" size={40} />
        <h2 className="text-xl font-bold mb-2">Call {ambulance.user.fname} {ambulance.user.lname}</h2>
        <p className="text-gray-600">Phone: {ambulance.user.phone}</p>
      </div>
      <div className="flex gap-4">
        <button onClick={() => setShowCallModal(false)} className="flex-1 py-2 bg-gray-200 rounded-lg hover:bg-gray-300">
          Cancel
        </button>
        <a href={`tel:${ambulance.user.phone}`} className="flex-1 py-2 bg-red-500 text-white rounded-lg text-center hover:bg-red-600">
          Call Now
        </a>
      </div>
    </div>
  </div>
)}

{/* Booking Modal */}
{showBookingModal && (
  <div onClick={() => { if (!bookingSuccess && !bookingError) setShowBookingModal(false) }} className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
    <div onClick={(e) => e.stopPropagation()} className="bg-white rounded-2xl p-6 max-w-md w-full text-center">

      {!bookingSuccess && !bookingError ? (
        <>
          <h2 className="text-xl font-bold mb-4">Confirm Booking</h2>
          <p className="text-gray-600 mb-6">Are you sure you want to book this ambulance?</p>
          <div className="flex gap-4">
            <button
              onClick={() => setShowBookingModal(false)}
              className="flex-1 py-2 bg-gray-200 rounded-lg hover:bg-gray-300"
            >
              No
            </button>
            <button
              onClick={async () => {
                try {
                  if (!ambulance) return;
                  const { data } = await api.post('/api/v1/ambulance/booking/create', {
                    ambulanceId: ambulance.id,
                  });
                  if (data.code === 'XS0001') {
                    setBookingSuccess(true);
                  } else {
                    setBookingError(true);
                  }
                } catch (error) {
                  setBookingError(true);
                }
              }}
              className="flex-1 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600"
            >
              Yes, Confirm
            </button>
          </div>
        </>
      ) : bookingSuccess ? (
        <>
          <h2 className="text-xl font-bold mb-4 text-green-600">Booking Successful!</h2>
          <p className="text-gray-600 mb-6">Your ambulance has been successfully booked.</p>
          <button
            onClick={() => { setShowBookingModal(false); setBookingSuccess(false); }}
            className="py-2 px-6 bg-green-500 text-white rounded-lg hover:bg-green-600"
          >
            Close
          </button>
        </>
      ) : (
        <>
          <h2 className="text-xl font-bold mb-4 text-red-600">Booking Failed!</h2>
          <p className="text-gray-600 mb-6">Something went wrong. Please try again later.</p>
          <button
            onClick={() => { setShowBookingModal(false); setBookingError(false); }}
            className="py-2 px-6 bg-red-500 text-white rounded-lg hover:bg-red-600"
          >
            Close
          </button>
        </>
      )}

    </div>
  </div>
)}


    </div>

    
  );
}
