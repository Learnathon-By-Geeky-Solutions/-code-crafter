import { Link } from 'react-router-dom';
import { Mail, MapPin, Phone, Facebook, Twitter, Instagram, Youtube, Globe } from 'lucide-react';

export function Footer() {
  const currentYear = new Date().getFullYear();

  return (
    <footer className="bg-gradient-to-b from-blue-600 to-blue-700 text-white">
      <div className="container mx-auto">
        {/* Main Footer Content */}
        <div className="grid grid-cols-1 md:grid-cols-4 gap-12 py-16 px-6 md:px-12">
          {/* Company Info */}
          <div>
            <Link to="/" className="text-2xl font-bold mb-6 block">XENON</Link>
            <p className="mb-6 text-blue-100">
              Xenon Healthcare provides progressive, and affordable healthcare, accessible on mobile and online for everyone.
            </p>
            <div className="space-y-3">
              <div className="flex items-center gap-3 text-blue-100">
                <MapPin size={18} />
                <span>123 Healthcare Avenue, Dhaka, Bangladesh</span>
              </div>
              <div className="flex items-center gap-3 text-blue-100">
                <Mail size={18} />
                <a href="mailto:contact@xenon.health" className="hover:text-white">contact@xenon.health</a>
              </div>
              <div className="flex items-center gap-3 text-blue-100">
                <Phone size={18} />
                <a href="tel:+8801234567890" className="hover:text-white">+880 1234-567890</a>
              </div>
            </div>
          </div>

          {/* Quick Links */}
          <div>
            <h3 className="font-semibold mb-6">Quick Links</h3>
            <ul className="space-y-3">
              <li>
                <Link to="/appointment" className="text-blue-100 hover:text-white">Book Appointment</Link>
              </li>
              <li>
                <Link to="/hospitals" className="text-blue-100 hover:text-white">Find Doctors</Link>
              </li>
              <li>
                <Link to="/medicine" className="text-blue-100 hover:text-white">Order Medicine</Link>
              </li>
              <li>
                <Link to="/blood-donation" className="text-blue-100 hover:text-white">Blood Donation</Link>
              </li>
              <li>
                <Link to="/ambulance" className="text-blue-100 hover:text-white">Ambulance Service</Link>
              </li>
            </ul>
          </div>

          {/* Services */}
          <div>
            <h3 className="font-semibold mb-6">Our Services</h3>
            <ul className="space-y-3">
              <li>
                <Link to="/appointment/emergency" className="text-blue-100 hover:text-white">Emergency Consultation</Link>
              </li>
              <li>
                <Link to="/appointment/specialized" className="text-blue-100 hover:text-white">Specialized Doctors</Link>
              </li>
              <li>
                <Link to="/community/doctors" className="text-blue-100 hover:text-white">Doctor Articles</Link>
              </li>
              <li>
                <Link to="/community/users" className="text-blue-100 hover:text-white">Health Community</Link>
              </li>
              <li>
                <Link to="/find-pharmacies" className="text-blue-100 hover:text-white">Find Pharmacy</Link>
              </li>
            </ul>
          </div>

          {/* Download & Social */}
          <div>
            <h3 className="font-semibold mb-6">Connect With Us</h3>
            <div className="flex gap-4 mb-8">
              <a href="#" className="bg-blue-500 p-2 rounded-full hover:bg-blue-400 transition-colors">
                <Facebook size={20} />
              </a>
              <a href="#" className="bg-blue-500 p-2 rounded-full hover:bg-blue-400 transition-colors">
                <Twitter size={20} />
              </a>
              <a href="#" className="bg-blue-500 p-2 rounded-full hover:bg-blue-400 transition-colors">
                <Instagram size={20} />
              </a>
              <a href="#" className="bg-blue-500 p-2 rounded-full hover:bg-blue-400 transition-colors">
                <Youtube size={20} />
              </a>
            </div>
            <h3 className="font-semibold mb-4">Languages</h3>
            <div className="flex items-center gap-2 text-blue-100">
              <Globe size={18} />
              <select className="bg-transparent border-none focus:ring-0 cursor-pointer">
                <option value="en">English</option>
                <option value="bn">বাংলা</option>
              </select>
            </div>
          </div>
        </div>

        {/* Bottom Bar */}
        <div className="border-t border-blue-500 py-6 px-6 md:px-12">
          <div className="flex flex-col md:flex-row justify-between items-center gap-4">
            <p className="text-blue-100">©{currentYear} Xenon Healthcare. All rights reserved</p>
            <div className="flex gap-6">
              <Link to="/about" className="text-blue-100 hover:text-white">About Us</Link>
              <Link to="/contact" className="text-blue-100 hover:text-white">Contact</Link>
              <a href="#" className="text-blue-100 hover:text-white">Privacy Policy</a>
              <a href="#" className="text-blue-100 hover:text-white">Terms of Service</a>
            </div>
          </div>
        </div>
      </div>
    </footer>
  );
}