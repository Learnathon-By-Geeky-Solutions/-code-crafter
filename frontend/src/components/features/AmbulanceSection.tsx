import { Link } from 'react-router-dom';
import { Truck, Heart, Thermometer, Clock, MapPin, Phone } from 'lucide-react';

export function AmbulanceSection() {
  const ambulanceTypes = [
    {
      id: 'general',
      name: 'General Ambulance',
      icon: <Truck className="w-12 h-12 text-red-500" />,
      description: 'Basic life support ambulance for non-critical patient transport',
      price: '৳ 2000/trip'
    },
    {
      id: 'icu',
      name: 'ICU Ambulance',
      icon: <Heart className="w-12 h-12 text-red-500" />,
      description: 'Advanced life support ambulance with ICU facilities',
      price: '৳ 5000/trip'
    },
    {
      id: 'freezing',
      name: 'Freezing Ambulance',
      icon: <Thermometer className="w-12 h-12 text-red-500" />,
      description: 'Specialized temperature-controlled transport service',
      price: '৳ 3000/trip'
    }
  ];

  return (
    <div className="py-16 px-6 md:px-12 bg-gradient-to-b from-red-50 to-white">
      <div className="max-w-6xl mx-auto">
        <div className="text-center mb-12">
          <h2 className="text-3xl font-bold mb-4">Ambulance Services</h2>
          <p className="text-gray-600 max-w-2xl mx-auto">
            24/7 emergency ambulance services with professional medical support
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-12">
          {ambulanceTypes.map((type) => (
            <Link
              key={type.id}
              to={`/ambulance/book/${type.id}`}
              className="bg-white rounded-xl p-6 shadow-sm hover:shadow-md transition-all text-center group"
            >
              <div className="flex flex-col items-center">
                {type.icon}
                <h3 className="text-xl font-semibold mt-4 mb-2 group-hover:text-red-500 transition-colors">
                  {type.name}
                </h3>
                <p className="text-gray-600 mb-4">{type.description}</p>
                <span className="text-red-500 font-semibold">{type.price}</span>
              </div>
            </Link>
          ))}
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <div className="bg-white p-6 rounded-xl shadow-sm">
            <div className="text-red-500 mb-4">
              <Clock size={32} />
            </div>
            <h3 className="text-lg font-semibold mb-2">24/7 Availability</h3>
            <p className="text-gray-600">
              Round-the-clock emergency response team ready to serve you anytime
            </p>
          </div>

          <div className="bg-white p-6 rounded-xl shadow-sm">
            <div className="text-red-500 mb-4">
              <MapPin size={32} />
            </div>
            <h3 className="text-lg font-semibold mb-2">Wide Coverage</h3>
            <p className="text-gray-600">
              Available across all major cities and locations
            </p>
          </div>

          <div className="bg-white p-6 rounded-xl shadow-sm">
            <div className="text-red-500 mb-4">
              <Phone size={32} />
            </div>
            <h3 className="text-lg font-semibold mb-2">Quick Response</h3>
            <p className="text-gray-600">
              Fast and efficient emergency medical transport services
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}