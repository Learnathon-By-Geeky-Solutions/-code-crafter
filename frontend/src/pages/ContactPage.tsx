import { useState } from 'react';
import { MapPin, Phone, Mail, Clock, CheckCircle } from 'lucide-react';

export function ContactPage() {
  const [isSubmitted, setIsSubmitted] = useState(false);

  const contactInfo = [
    {
      icon: <MapPin className="text-blue-500" size={24} />,
      title: 'Visit Us',
      details: ['123 Healthcare Avenue', 'Medical District', 'City, Country 12345']
    },
    {
      icon: <Phone className="text-blue-500" size={24} />,
      title: 'Call Us',
      details: ['+1 (555) 123-4567', '+1 (555) 987-6543']
    },
    {
      icon: <Mail className="text-blue-500" size={24} />,
      title: 'Email Us',
      details: ['support@xenonhealthcare.com', 'info@xenonhealthcare.com']
    },
    {
      icon: <Clock className="text-blue-500" size={24} />,
      title: 'Working Hours',
      details: ['Monday - Friday: 9:00 AM - 8:00 PM', 'Saturday: 9:00 AM - 5:00 PM']
    }
  ];

  return (
    <div className="bg-white">
      {/* Hero Section */}
      <div className="relative h-[300px] overflow-hidden">
        <img
          src="https://images.unsplash.com/photo-1577962917302-cd874c4e31d2?auto=format&fit=crop&w=1920&q=80"
          alt="Contact us"
          className="w-full h-full object-cover"
        />
        <div className="absolute inset-0 bg-blue-900 bg-opacity-70 flex items-center justify-center">
          <div className="text-center text-white">
            <h1 className="text-4xl font-bold mb-4">Contact Us</h1>
            <p className="text-xl">We're here to help and answer any questions you might have</p>
          </div>
        </div>
      </div>

      <div className="max-w-6xl mx-auto py-16 px-6 md:px-12">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-12">
          {/* Contact Form / Success Message */}
          <div>
            {isSubmitted ? (
              <div className="bg-white rounded-lg shadow-lg p-8 text-center">
                <div className="flex justify-center mb-6">
                  <div className="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center">
                    <CheckCircle className="w-8 h-8 text-green-500" />
                  </div>
                </div>
                <h2 className="text-2xl font-bold text-gray-900 mb-4">Message Sent Successfully!</h2>
                <p className="text-gray-600 mb-6">
                  Thank you for reaching out to us. We have received your message and will get back to you within 24 hours.
                </p>
                <button
                  onClick={() => setIsSubmitted(false)}
                  className="inline-block px-6 py-3 bg-blue-500 text-white rounded-full hover:bg-blue-600 transition-colors"
                >
                  Send Another Message
                </button>
              </div>
            ) : (
              <div>
                <h2 className="text-2xl font-bold mb-6">Send us a Message</h2>
                <form 
                  onSubmit={(e) => {
                    e.preventDefault();
                    setIsSubmitted(true);
                  }} 
                  className="space-y-6"
                >
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      Your Name <span className="text-red-500">*</span>
                    </label>
                    <input
                      type="text"
                      required
                      className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                    />
                  </div>

                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      Email Address <span className="text-red-500">*</span>
                    </label>
                    <input
                      type="email"
                      required
                      className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                    />
                  </div>

                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      Subject <span className="text-red-500">*</span>
                    </label>
                    <input
                      type="text"
                      required
                      className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                    />
                  </div>

                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      Message <span className="text-red-500">*</span>
                    </label>
                    <textarea
                      required
                      rows={6}
                      className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                    />
                  </div>

                  <button
                    type="submit"
                    className="w-full py-3 bg-blue-500 text-white rounded-full hover:bg-blue-600 transition-colors"
                  >
                    Send Message
                  </button>
                </form>
              </div>
            )}
          </div>

          {/* Contact Information */}
          <div>
            <h2 className="text-2xl font-bold mb-6">Get in Touch</h2>
            <div className="grid grid-cols-1 gap-8">
              {contactInfo.map((info, index) => (
                <div key={index} className="flex gap-4">
                  <div className="flex-shrink-0">
                    {info.icon}
                  </div>
                  <div>
                    <h3 className="font-semibold mb-2">{info.title}</h3>
                    {info.details.map((detail, i) => (
                      <p key={i} className="text-gray-600">{detail}</p>
                    ))}
                  </div>
                </div>
              ))}
            </div>

            {/* Map */}
            <div className="mt-8">
              <iframe
                src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d387193.30596552044!2d-74.25986867659859!3d40.69714941680757!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x89c24fa5d33f083b%3A0xc80b8f06e177fe62!2sNew%20York%2C%20NY%2C%20USA!5e0!3m2!1sen!2s!4v1647043276541!5m2!1sen!2s"
                width="100%"
                height="300"
                style={{ border: 0 }}
                allowFullScreen
                loading="lazy"
                className="rounded-lg"
              ></iframe>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}