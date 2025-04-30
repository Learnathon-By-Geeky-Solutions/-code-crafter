import { Link } from 'react-router-dom';
import { Button } from '../ui/Button';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Autoplay, Pagination, Navigation } from 'swiper/modules';
import { useState, useEffect } from 'react';
import 'swiper/css';
import 'swiper/css/pagination';
import 'swiper/css/navigation';

export function Hero() {
  const [statusMessage, setStatusMessage] = useState('Emergency Ambulance Available');

  const statusMessages = [
    'Emergency Ambulance Available',
    'Online Medicine Store Open',
    'Blood Donation Required',
    'Join Our Community',
    'Online & Offline Appointments',
    'Telemedicine Service Available',
    'Find Nearest Hospital',
    'Expert Doctors Online'
  ];

  useEffect(() => {
    const interval = setInterval(() => {
      setStatusMessage(prev => {
        const currentIndex = statusMessages.indexOf(prev);
        return statusMessages[(currentIndex + 1) % statusMessages.length];
      });
    }, 3000);

    return () => clearInterval(interval);
  }, []);

  const slides = [
    {
      image: "https://images.unsplash.com/photo-1576091160550-2173dba999ef?auto=format&fit=crop&w=1920&q=80",
      title: "Virtual healthcare for you",
      description: "Virtual provider programs and dedicated services, available on-mobile and online healthcare platform."
    },
    {
      image: "https://images.unsplash.com/photo-1631217868264-e5b90bb7e133?auto=format&fit=crop&w=1920&q=80",
      title: "Professional Healthcare",
      description: "Get access to qualified doctors and medical professionals for the best healthcare service."
    },
    {
      image: "https://images.unsplash.com/photo-1666214280557-f1b5022eb634?auto=format&fit=crop&w=1920&q=80",
      title: "24/7 Emergency Service",
      description: "Round-the-clock emergency medical services with quick response time and expert care."
    },
    {
      image: "https://images.unsplash.com/photo-1584982751601-97dcc096659c?auto=format&fit=crop&w=1920&q=80",
      title: "Blood Donation Network",
      description: "Join our blood donation community to help save lives. Connect with donors in real-time."
    },
    {
      image: "https://images.unsplash.com/photo-1587854692152-cbe660dbde88?auto=format&fit=crop&w=1920&q=80",
      title: "Online Medicine Store",
      description: "Order medicines online from verified pharmacies with doorstep delivery service."
    }
  ];

  return (
    <div className="relative h-[600px] overflow-hidden">
      <Swiper
        modules={[Autoplay, Pagination, Navigation]}
        spaceBetween={0}
        slidesPerView={1}
        autoplay={{
          delay: 5000,
          disableOnInteraction: false,
        }}
        pagination={{
          clickable: true,
          bulletClass: 'swiper-pagination-bullet !bg-white !opacity-50',
          bulletActiveClass: 'swiper-pagination-bullet-active !bg-white !opacity-100',
        }}
        navigation={{
          nextEl: '.swiper-button-next',
          prevEl: '.swiper-button-prev',
        }}
        className="h-full"
      >
        {slides.map((slide, index) => (
          <SwiperSlide key={index}>
            <div className="relative h-full">
              <img 
                src={slide.image}
                alt={slide.title}
                className="w-full h-full object-cover"
              />
              <div className="absolute inset-0 bg-gradient-to-r from-black/80 to-black/60" />
              <div className="absolute inset-0 flex items-center">
                <div className="container mx-auto px-6 md:px-12">
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-8 items-center">
                    <div>
                      <h1 className="text-4xl md:text-5xl font-bold mb-4 text-white">
                        {slide.title}
                      </h1>
                      <p className="text-lg text-white/90 mb-8">
                        {slide.description}
                      </p>
                      <div className="flex gap-4">
                        <Link to="/blood-donation">
                          <Button>
                            Blood Donation
                          </Button>
                        </Link>
                        <Link to="/ambulance">
                          <Button variant="secondary">
                            Ambulances
                          </Button>
                        </Link>
                      </div>
                    </div>
                    <div className="hidden md:block">
                      <div className="relative">
                        {/* YouTube Video */}
                        <div className="absolute -inset-4 bg-blue-500/20 rounded-lg blur-lg animate-pulse"></div>
                        <div className="relative bg-black/30 backdrop-blur-sm rounded-lg p-2 transform hover:scale-105 transition-transform duration-300">
                          <iframe
                            width="100%"
                            height="315"
                            src="https://www.youtube.com/embed/E0N4YtQY6LE?autoplay=1&mute=1&loop=1&playlist=E0N4YtQY6LE"
                            title="Healthcare Video"
                            frameBorder="0"
                            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                            allowFullScreen
                            className="rounded-lg shadow-2xl"
                          ></iframe>
                          <div className="absolute bottom-4 left-4 right-4">
                            <div className="bg-black/40 backdrop-blur-md rounded-lg p-4">
                              <div className="flex items-center gap-3">
                                <div className="w-3 h-3 bg-red-500 rounded-full animate-pulse"></div>
                                <span className="text-white text-sm font-medium animate-fade-in-out">
                                  {statusMessage}
                                </span>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </SwiperSlide>
        ))}
      </Swiper>

      {/* Custom Navigation Buttons */}
      <div className="swiper-button-prev !text-white !opacity-50 hover:!opacity-100 transition-opacity" />
      <div className="swiper-button-next !text-white !opacity-50 hover:!opacity-100 transition-opacity" />

      {/* Custom styles for pagination */}
      <style>
        {`
          .swiper-pagination-bullet {
            width: 10px;
            height: 10px;
            margin: 0 6px;
          }
          @keyframes fadeInOut {
            0%, 100% { opacity: 0.8; }
            50% { opacity: 1; }
          }
          .animate-fade-in-out {
            animation: fadeInOut 2s ease-in-out infinite;
          }
        `}
      </style>
    </div>
  );
}