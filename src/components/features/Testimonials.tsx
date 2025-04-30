import { useEffect, useState } from 'react';
import { ChevronLeft, ChevronRight } from 'lucide-react';

export function Testimonials() {
  const [currentSlide, setCurrentSlide] = useState(0);

  const testimonials = [
    {
      id: 1,
      name: "Kabir Ahmed",
      role: "Patient",
      image: "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?auto=format&fit=crop&w=100&h=100&q=80",
      text: "Our dedicated patient engagement app and web portal allow you to access information instantaneously (no tedious form, long calls, or administrative hassle) and securely"
    },
    {
      id: 2,
      name: "Sarah Johnson",
      role: "Healthcare Professional",
      image: "https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=100&h=100&q=80",
      text: "The platform has revolutionized how we interact with patients. The virtual consultation features are seamless and the patient management system is intuitive."
    },
    {
      id: 3,
      name: "Michael Chen",
      role: "Regular User",
      image: "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=100&h=100&q=80",
      text: "Being able to track my health metrics and schedule appointments from my phone has made managing my health so much easier. The reminders are a lifesaver!"
    },
    {
      id: 4,
      name: "Emily Rodriguez",
      role: "Pharmacist",
      image: "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?auto=format&fit=crop&w=100&h=100&q=80",
      text: "The integration with local pharmacies has streamlined the prescription process. Patients love the convenience of home delivery options."
    },
    {
      id: 5,
      name: "David Kim",
      role: "Patient",
      image: "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?auto=format&fit=crop&w=100&h=100&q=80",
      text: "The emergency care feature gave me peace of mind when my child had a late-night fever. Getting professional advice quickly was invaluable."
    },
    {
      id: 6,
      name: "Lisa Patel",
      role: "Healthcare Administrator",
      image: "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&w=100&h=100&q=80",
      text: "Managing patient records and appointments has never been more efficient. The analytics tools help us improve our service delivery continuously."
    },
    {
      id: 7,
      name: "James Wilson",
      role: "Elderly Patient",
      image: "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?auto=format&fit=crop&w=100&h=100&q=80",
      text: "Despite my initial hesitation with technology, the app's user-friendly interface made it easy for me to keep track of my medications and appointments."
    },
    {
      id: 8,
      name: "Maria Garcia",
      role: "Nurse Practitioner",
      image: "https://images.unsplash.com/photo-1544005313-94ddf0286df2?auto=format&fit=crop&w=100&h=100&q=80",
      text: "The platform helps us provide better continuity of care. Being able to access patient history and previous consultations instantly makes our job much more effective."
    },
    {
      id: 9,
      name: "Thomas Anderson",
      role: "Regular User",
      image: "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=100&h=100&q=80",
      text: "The health tracking features have helped me maintain my fitness goals. The integration with wearable devices is seamless."
    },
    {
      id: 10,
      name: "Sophie Taylor",
      role: "Mental Health Professional",
      image: "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?auto=format&fit=crop&w=100&h=100&q=80",
      text: "The teletherapy features are excellent. The secure video conferencing and session notes management help me provide better care to my patients."
    },
    {
      id: 11,
      name: "Robert Martinez",
      role: "Patient",
      image: "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?auto=format&fit=crop&w=100&h=100&q=80",
      text: "Having all my health records in one place and being able to share them securely with different healthcare providers has made managing my chronic condition much easier."
    }
  ];

  useEffect(() => {
    const timer = setInterval(() => {
      setCurrentSlide((prev) => (prev + 1) % testimonials.length);
    }, 5000);
    return () => clearInterval(timer);
  }, [testimonials.length]);

  return (
    <div className="py-16 px-6 md:px-12">
      <h2 className="text-3xl font-bold text-center mb-12">What our customers are saying</h2>
      
      <div className="max-w-3xl mx-auto bg-blue-500 rounded-2xl p-8 text-white relative">
        <div className="flex items-center gap-4 mb-6">
          <img 
            src={testimonials[currentSlide].image}
            alt={testimonials[currentSlide].name}
            className="w-12 h-12 rounded-full object-cover"
          />
          <div>
            <h4 className="font-semibold">{testimonials[currentSlide].name}</h4>
            <p className="text-blue-100">{testimonials[currentSlide].role}</p>
          </div>
        </div>
        <p className="text-lg">{testimonials[currentSlide].text}</p>
        
        <div className="flex justify-center gap-2 mt-8">
          {testimonials.map((_, index) => (
            <button
              key={index}
              className={`w-2 h-2 rounded-full ${
                index === currentSlide ? 'bg-white' : 'bg-blue-300'
              }`}
              onClick={() => setCurrentSlide(index)}
            />
          ))}
        </div>
        
        <button 
          className="absolute left-4 top-1/2 -translate-y-1/2 hover:bg-blue-600 rounded-full p-2 transition-colors"
          onClick={() => setCurrentSlide((prev) => (prev - 1 + testimonials.length) % testimonials.length)}
        >
          <ChevronLeft size={24} />
        </button>
        <button 
          className="absolute right-4 top-1/2 -translate-y-1/2 hover:bg-blue-600 rounded-full p-2 transition-colors"
          onClick={() => setCurrentSlide((prev) => (prev + 1) % testimonials.length)}
        >
          <ChevronRight size={24} />
        </button>
      </div>
    </div>
  );
}