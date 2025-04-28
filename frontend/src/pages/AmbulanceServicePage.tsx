import { useState } from 'react';
import { Truck, Thermometer, Heart, Phone, MapPin, Clock, ArrowRight } from 'lucide-react';
import { Link } from 'react-router-dom';

interface AmbulanceType {
  id: string;
  name: string;
  icon: React.ReactNode;
  description: string;
  features: string[];
  price: string;
}

const ambulanceTypes: AmbulanceType[] = [
  {
    id: 'general',
    name: 'General Ambulance',
    icon: <Truck className="w-12 h-12 text-red-500" />,
    description: 'Basic life support ambulance for non-critical patient transport',
    features: [
      'Basic medical equipment',
      'Trained paramedic staff',
      'Patient stretcher',
      'First aid supplies',
      'Oxygen support'
    ],
    price: '৳ 2000/trip'
  },
  {
    id: 'icu',
    name: 'ICU Ambulance',
    icon: <Heart className="w-12 h-12 text-red-500" />,
    description: 'Advanced life support ambulance with ICU facilities',
    features: [
      'Full ICU equipment',
      'Specialized medical team',
      'Ventilator support',
      'Cardiac monitoring',
      'Emergency medications'
    ],
    price: '৳ 5000/trip'
  },
  {
    id: 'freezing',
    name: 'Freezing Ambulance',
    icon: <Thermometer className="w-12 h-12 text-red-500" />,
    description: 'Specialized temperature-controlled transport service',
    features: [
      'Temperature control system',
      'Specialized storage',
      'Professional handling',
      '24/7 availability',
      'Long-distance transport'
    ],
    price: '৳ 3000/trip'
  }
];

export function AmbulanceServicePage() {
  const [selectedType, setSelectedType] = useState<string | null>(null);

  return (
    <div className="container mx-auto px-4 py-12">
      <div className="max-w-4xl mx-auto">
        <div className="text-center mb-12">
          <h1 className="text-3xl font-bold mb-4">Ambulance Services</h1>
          <p className="text-gray-600">
            24/7 emergency ambulance services with professional medical support
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-12">
          {ambulanceTypes.map((type) => (
            <div
              key={type.id}
              className={`bg-white rounded-xl shadow-sm p-6 cursor-pointer transition-all ${
                selectedType === type.id
                  ? 'ring-2 ring-red-500 shadow-md'
                  : 'hover:shadow-md'
              }`}
              onClick={() => setSelectedType(type.id)}
            >
              <div className="flex flex-col items-center text-center">
                {type.icon}
                <h3 className="text-xl font-semibold mt-4 mb-2">{type.name}</h3>
                <p className="text-gray-600 text-sm mb-4">{type.description}</p>
                <span className="text-red-500 font-semibold">{type.price}</span>
              </div>
            </div>
          ))}
        </div>

        {selectedType && (
          <div className="bg-white rounded-xl shadow-sm p-6">
            <h2 className="text-xl font-semibold mb-6">
              {ambulanceTypes.find(t => t.id === selectedType)?.name} Features
            </h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <ul className="space-y-3">
                  {ambulanceTypes
                    .find(t => t.id === selectedType)
                    ?.features.map((feature, index) => (
                      <li key={index} className="flex items-center gap-2">
                        <ArrowRight className="text-red-500" size={16} />
                        <span>{feature}</span>
                      </li>
                    ))}
                </ul>
              </div>
              <div className="bg-red-50 rounded-lg p-6">
                <h3 className="font-semibold mb-4">Book Now</h3>
                <div className="space-y-4">
                  <div className="flex items-center gap-2">
                    <Phone size={20} className="text-red-500" />
                    <span>Emergency: +880 1234-567890</span>
                  </div>
                  <div className="flex items-center gap-2">
                    <MapPin size={20} className="text-red-500" />
                    <span>Available in all major cities</span>
                  </div>
                  <div className="flex items-center gap-2">
                    <Clock size={20} className="text-red-500" />
                    <span>24/7 Service</span>
                  </div>
                </div>
                <Link
                  to={`/ambulance/book/${selectedType}`}
                  className="mt-6 block w-full py-2 px-4 bg-red-500 text-white text-center rounded-full hover:bg-red-600 transition-colors"
                >
                  See Details
                </Link>
              </div>
            </div>
          </div>
        )}

        <div className="mt-12 bg-gray-50 rounded-xl p-6">
          <h2 className="text-xl font-semibold mb-4">Why Choose Our Ambulance Service?</h2>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            <div className="text-center">
              <div className="bg-red-100 w-12 h-12 rounded-full flex items-center justify-center mx-auto mb-4">
                <Clock className="text-red-500" />
              </div>
              <h3 className="font-semibold mb-2">24/7 Availability</h3>
              <p className="text-gray-600 text-sm">
                Round-the-clock emergency response team ready to serve
              </p>
            </div>
            <div className="text-center">
              <div className="bg-red-100 w-12 h-12 rounded-full flex items-center justify-center mx-auto mb-4">
                <Heart className="text-red-500" />
              </div>
              <h3 className="font-semibold mb-2">Professional Care</h3>
              <p className="text-gray-600 text-sm">
                Experienced medical staff and modern equipment
              </p>
            </div>
            <div className="text-center">
              <div className="bg-red-100 w-12 h-12 rounded-full flex items-center justify-center mx-auto mb-4">
                <MapPin className="text-red-500" />
              </div>
              <h3 className="font-semibold mb-2">Wide Coverage</h3>
              <p className="text-gray-600 text-sm">
                Service available across all major locations
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}