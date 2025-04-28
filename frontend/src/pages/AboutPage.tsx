import { Users, Award, Globe, Heart } from 'lucide-react';

export function AboutPage() {
  const stats = [
    { icon: <Users className="text-blue-500" size={32} />, value: '10K+', label: 'Active Users' },
    { icon: <Award className="text-blue-500" size={32} />, value: '500+', label: 'Expert Doctors' },
    { icon: <Globe className="text-blue-500" size={32} />, value: '50+', label: 'Cities' },
    { icon: <Heart className="text-blue-500" size={32} />, value: '98%', label: 'Satisfaction Rate' }
  ];

  const team = [
    {
      name: 'Dr. Sarah Johnson',
      role: 'Chief Medical Officer',
      image: 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=300&h=300&q=80',
      bio: 'With over 15 years of experience in healthcare management and patient care.'
    },
    {
      name: 'Michael Chen',
      role: 'Chief Technology Officer',
      image: 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=300&h=300&q=80',
      bio: 'Leading our technical innovations with a focus on accessibility and user experience.'
    },
    {
      name: 'Dr. Emily Rodriguez',
      role: 'Head of Telemedicine',
      image: 'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?auto=format&fit=crop&w=300&h=300&q=80',
      bio: 'Pioneering virtual healthcare solutions to make medical care more accessible.'
    }
  ];

  return (
    <div className="bg-white">
      {/* Hero Section */}
      <div className="relative h-[400px] overflow-hidden">
        <img
          src="https://images.unsplash.com/photo-1504813184591-01572f98c85f?auto=format&fit=crop&w=1920&q=80"
          alt="Medical team"
          className="w-full h-full object-cover"
        />
        <div className="absolute inset-0 bg-blue-900 bg-opacity-70 flex items-center justify-center">
          <div className="text-center text-white">
            <h1 className="text-4xl md:text-5xl font-bold mb-4">About Xenon Healthcare</h1>
            <p className="text-xl max-w-2xl mx-auto">
              Revolutionizing healthcare through technology and compassion
            </p>
          </div>
        </div>
      </div>

      {/* Mission Section */}
      <div className="py-16 px-6 md:px-12">
        <div className="max-w-3xl mx-auto text-center">
          <h2 className="text-3xl font-bold mb-6">Our Mission</h2>
          <p className="text-gray-600 text-lg mb-8">
            At Xenon Healthcare, we're committed to making quality healthcare accessible to everyone through innovative technology and a patient-first approach. Our platform connects patients with healthcare providers, streamlines medical processes, and ensures the best possible care delivery.
          </p>
          <div className="grid grid-cols-2 md:grid-cols-4 gap-8">
            {stats.map((stat, index) => (
              <div key={index} className="text-center">
                <div className="flex justify-center mb-3">{stat.icon}</div>
                <div className="text-2xl font-bold text-gray-900">{stat.value}</div>
                <div className="text-gray-500">{stat.label}</div>
              </div>
            ))}
          </div>
        </div>
      </div>

      {/* Values Section */}
      <div className="bg-gray-50 py-16 px-6 md:px-12">
        <div className="max-w-6xl mx-auto">
          <h2 className="text-3xl font-bold text-center mb-12">Our Values</h2>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            <div className="bg-white p-8 rounded-xl shadow-sm">
              <h3 className="text-xl font-semibold mb-4">Innovation</h3>
              <p className="text-gray-600">
                We continuously strive to improve healthcare delivery through cutting-edge technology and creative solutions.
              </p>
            </div>
            <div className="bg-white p-8 rounded-xl shadow-sm">
              <h3 className="text-xl font-semibold mb-4">Accessibility</h3>
              <p className="text-gray-600">
                Making quality healthcare available to everyone, regardless of location or circumstances.
              </p>
            </div>
            <div className="bg-white p-8 rounded-xl shadow-sm">
              <h3 className="text-xl font-semibold mb-4">Excellence</h3>
              <p className="text-gray-600">
                Maintaining the highest standards in healthcare service delivery and patient care.
              </p>
            </div>
          </div>
        </div>
      </div>

      {/* Team Section */}
      <div className="py-16 px-6 md:px-12">
        <div className="max-w-6xl mx-auto">
          <h2 className="text-3xl font-bold text-center mb-12">Leadership Team</h2>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            {team.map((member, index) => (
              <div key={index} className="text-center">
                <img
                  src={member.image}
                  alt={member.name}
                  className="w-48 h-48 rounded-full mx-auto mb-4 object-cover"
                />
                <h3 className="text-xl font-semibold mb-2">{member.name}</h3>
                <p className="text-blue-500 mb-3">{member.role}</p>
                <p className="text-gray-600">{member.bio}</p>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}