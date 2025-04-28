import { ArrowLeft, FileUp, Clock, Calendar as CalendarIcon, User, Phone, MapPin, CreditCard, Shield, Video, Clock3, FileText } from 'lucide-react';
import { Link, useNavigate, useParams, useLocation } from 'react-router-dom';
import { useState } from 'react';
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";

export function BookTelemedicinePage() {
  const { type, specialty, id, hospitalId, departmentId } = useParams();
  const navigate = useNavigate();
  const location = useLocation();
  const [selectedDate, setSelectedDate] = useState<Date | null>(null);
  const [selectedTime, setSelectedTime] = useState<string>('');

  const isOfflineConsultation = location.pathname.includes('/hospitals/');

  // Available time slots
  const timeSlots = [
    "10:00 PM to 11:40 PM",
    "11:40 PM to 01:20 AM",
    "01:20 AM to 03:00 AM"
  ];

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const bookingData = {
      date: selectedDate?.toISOString(),
      time: selectedTime
    };

    const nextPath = isOfflineConsultation
      ? `/hospitals/${hospitalId}/departments/${departmentId}/doctor/${id}/payment`
      : `/appointment/${type}/${specialty}/doctor/${id}/payment`;

    navigate(nextPath, { state: { bookingData } });
  };

  return (
    <div className="min-h-screen bg-gradient-to-b from-blue-50 to-white">
      <div className="container mx-auto px-4 py-8">
        <div className="flex items-center mb-8">
          <Link 
            to={isOfflineConsultation 
              ? `/hospitals/${hospitalId}/departments/${departmentId}/doctor/${id}`
              : `/appointment/${type}/${specialty}/doctor/${id}`
            } 
            className="flex items-center text-gray-600 hover:text-gray-800 transition-colors"
          >
            <ArrowLeft className="w-5 h-5 mr-2" />
            Back
          </Link>
        </div>

        <div className="max-w-4xl mx-auto">
          {/* Doctor Info Card */}
          <div className="bg-white rounded-2xl shadow-lg p-6 mb-8">
            <div className="flex items-center gap-6 border-b pb-6">
              <img
                src="https://images.unsplash.com/photo-1537368910025-700350fe46c7?auto=format&fit=crop&w=150&h=150&q=80"
                alt="Dr Masum Jia"
                className="w-24 h-24 rounded-2xl object-cover shadow-md"
              />
              <div>
                <h2 className="text-2xl font-bold mb-1">Dr Masum Jia</h2>
                <p className="text-blue-600 font-medium mb-1">MBBS, No: A101146</p>
                <p className="text-gray-600">Liver Specialist</p>
                <div className="flex items-center gap-4 mt-2 text-sm text-gray-500">
                  <span className="flex items-center gap-1">
                    <Clock3 size={16} />
                    5 years experience
                  </span>
                  <span className="flex items-center gap-1">
                    <FileText size={16} />
                    1000+ consultations
                  </span>
                </div>
              </div>
            </div>

            <div className="grid grid-cols-3 gap-6 py-6 border-b">
              <div className="text-center">
                <div className="bg-blue-50 w-12 h-12 rounded-full flex items-center justify-center mx-auto mb-2">
                  <Video className="text-blue-500" size={24} />
                </div>
                <h3 className="font-medium">Online Consultation</h3>
                <p className="text-sm text-gray-500">Video call with doctor</p>
              </div>
              <div className="text-center">
                <div className="bg-green-50 w-12 h-12 rounded-full flex items-center justify-center mx-auto mb-2">
                  <Clock className="text-green-500" size={24} />
                </div>
                <h3 className="font-medium">30 Minutes</h3>
                <p className="text-sm text-gray-500">Consultation duration</p>
              </div>
              <div className="text-center">
                <div className="bg-purple-50 w-12 h-12 rounded-full flex items-center justify-center mx-auto mb-2">
                  <Shield className="text-purple-500" size={24} />
                </div>
                <h3 className="font-medium">Secure</h3>
                <p className="text-sm text-gray-500">End-to-end encrypted</p>
              </div>
            </div>

            <div className="pt-6">
              <h3 className="font-semibold mb-2">Important Notes:</h3>
              <ul className="space-y-2 text-sm text-gray-600">
                <li>• Please join the consultation 5 minutes before the scheduled time</li>
                <li>• Ensure you have a stable internet connection</li>
                <li>• Keep your medical history and reports ready</li>
              </ul>
            </div>
          </div>

          {/* Booking Form */}
          <form onSubmit={handleSubmit} className="bg-white rounded-2xl shadow-lg p-8">
            <h2 className="text-2xl font-bold mb-8 text-center">Schedule Your Appointment</h2>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Select Date <span className="text-red-500">*</span>
                </label>
                <div className="relative">
                  <CalendarIcon className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
                  <DatePicker
                    selected={selectedDate}
                    onChange={(date) => setSelectedDate(date)}
                    minDate={new Date()}
                    maxDate={new Date().setDate(new Date().getDate() + 30)}
                    dateFormat="MMMM d, yyyy"
                    placeholderText="Select appointment date"
                    className="pl-10 w-full p-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-blue-200 shadow-sm"
                    required
                  />
                </div>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Select Time <span className="text-red-500">*</span>
                </label>
                <div className="grid grid-cols-1 gap-2">
                  {timeSlots.map((slot) => (
                    <button
                      key={slot}
                      type="button"
                      className={`p-3 rounded-xl border-2 flex items-center justify-center gap-2 transition-all ${
                        selectedTime === slot
                          ? 'border-blue-500 bg-blue-50 text-blue-700 shadow-sm'
                          : 'border-gray-200 hover:border-blue-300 hover:bg-gray-50'
                      }`}
                      onClick={() => setSelectedTime(slot)}
                    >
                      <Clock size={16} />
                      <span className="font-medium">{slot}</span>
                    </button>
                  ))}
                </div>
              </div>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-8 mt-8">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Beneficiary <span className="text-red-500">*</span>
                </label>
                <select className="w-full p-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-blue-200 shadow-sm" required>
                  <option value="">Select beneficiary</option>
                  <option value="myself">MySelf</option>
                  <option value="other">Other</option>
                </select>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Name <span className="text-red-500">*</span>
                </label>
                <div className="relative">
                  <User className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
                  <input 
                    type="text"
                    placeholder="Enter full name"
                    className="pl-10 w-full p-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-blue-200 shadow-sm"
                    required
                  />
                </div>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Mobile <span className="text-red-500">*</span>
                </label>
                <div className="relative">
                  <Phone className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
                  <input 
                    type="tel"
                    placeholder="Enter mobile number"
                    className="pl-10 w-full p-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-blue-200 shadow-sm"
                    required
                  />
                </div>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Address <span className="text-red-500">*</span>
                </label>
                <div className="relative">
                  <MapPin className="absolute left-3 top-3 text-gray-400" size={20} />
                  <textarea
                    placeholder="Enter your address"
                    className="pl-10 w-full p-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-blue-200 shadow-sm"
                    rows={1}
                    required
                  />
                </div>
              </div>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-8 mt-8">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Gender <span className="text-red-500">*</span>
                </label>
                <select className="w-full p-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-blue-200 shadow-sm" required>
                  <option value="">Select gender</option>
                  <option value="male">Male</option>
                  <option value="female">Female</option>
                  <option value="other">Other</option>
                </select>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Age <span className="text-red-500">*</span>
                </label>
                <input 
                  type="number"
                  min="0"
                  max="150"
                  placeholder="Enter age"
                  className="w-full p-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-blue-200 shadow-sm"
                  required
                />
              </div>
            </div>

            <div className="mt-8">
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Attach Medical History <span className="text-gray-400">(Optional)</span>
              </label>
              <label 
                htmlFor="file-upload" 
                className="border-2 border-dashed border-gray-300 rounded-xl p-8 flex flex-col items-center justify-center cursor-pointer hover:border-blue-300 transition-colors bg-gray-50"
              >
                <FileUp size={32} className="text-gray-400 mb-4" />
                <p className="text-sm text-gray-500 mb-1">Click to upload or drag and drop</p>
                <p className="text-xs text-gray-400">JPG/PNG Format Only (Max 5MB)</p>
                <input
                  id="file-upload"
                  type="file"
                  className="hidden"
                  accept=".jpg,.jpeg,.png"
                />
              </label>
            </div>

            <div className="mt-8 bg-blue-50 rounded-xl p-6">
              <h3 className="font-semibold mb-4">Price Details</h3>
              <div className="space-y-2">
                <div className="flex justify-between">
                  <span className="text-gray-600">Consultation Fee</span>
                  <span>৳ 200</span>
                </div>
                <div className="flex justify-between text-green-600">
                  <span>Discount</span>
                  <span>৳ 0</span>
                </div>
                <div className="flex justify-between font-medium pt-2 border-t border-blue-100">
                  <span>Total Amount</span>
                  <span>৳ 200</span>
                </div>
              </div>
            </div>

            <button
              type="submit"
              className="w-full mt-8 py-4 bg-gradient-to-r from-blue-500 to-blue-600 text-white rounded-xl hover:from-blue-600 hover:to-blue-700 transition-all shadow-lg hover:shadow-xl font-medium text-lg flex items-center justify-center gap-2"
            >
              <CreditCard size={20} />
              Proceed to Payment
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}