import { Search, Pill, Phone, Users, Siren, FileSpreadsheet } from 'lucide-react';
import { ServiceCard } from '../ui/ServiceCard';
import { Link } from 'react-router-dom';

export function Services() {
  const services = [
    {
      icon: <Search size={24} />,
      title: "Search doctor",
      description: "Choose your doctor from thousands of specialist, general, and trusted hospitals"
    },
    {
      icon: <Pill size={24} />,
      title: "Online pharmacy",
      description: "Buy your medicines with our mobile application with a simple delivery system"
    },
    {
      icon: <Phone size={24} />,
      title: "Consultation",
      description: "Free consultation with our trusted doctors and get the best recommendations"
    },
    {
      icon: <Users size={24} />,
      title: "Community Service",
      description: "Connect with healthcare professionals and share experiences"
    },
    {
      icon: <Siren size={24} />,
      title: "Emergency care",
      description: "You can get 24/7 urgent care for yourself or your children and your lovely family"
    },
    {
      icon: <FileSpreadsheet size={24} />,
      title: "Tracking",
      description: "Track and save your medical history and health data"
    }
  ];

  return (
    <div className="py-16 px-6 md:px-12 bg-gray-50">
      <h2 className="text-3xl font-bold text-center mb-4">Our services</h2>
      <p className="text-gray-600 text-center mb-12 max-w-2xl mx-auto">
        We provide to you the best choices for you. Adjust it to your health needs and make sure you undergo treatment with our highly qualified doctors.
      </p>
      
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        {services.map((service, index) => (
          <ServiceCard key={index} {...service} />
        ))}
      </div>
    </div>
  );
}