import { ArrowLeft, FileUp, CreditCard, Phone, User, MapPin, Shield, AlertCircle, CheckCircle } from 'lucide-react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { useState } from 'react';

export function PaymentPage() {
  const { type, specialty, id } = useParams<{ type: string; specialty: string; id: string }>();
  const navigate = useNavigate();
  const [paymentMethod, setPaymentMethod] = useState('');
  const [phone, setPhone] = useState('');
  const [transactionId, setTransactionId] = useState('');
  const [screenshot, setScreenshot] = useState<File | null>(null);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!paymentMethod || !phone || !transactionId) {
      alert('Please fill in all required fields');
      return;
    }
    navigate(`/appointment/${type}/${specialty}/doctor/${id}/confirmation`);
  };

  const paymentMethods = [
    {
      id: 'bkash',
      name: 'bKash',
      logo: 'https://images.unsplash.com/photo-1622253692010-333f2da6031d?auto=format&fit=crop&w=50&h=50&q=80',
      number: '01703098052',
      color: 'bg-pink-50 border-pink-200 text-pink-600'
    },
    {
      id: 'nagad',
      name: 'Nagad',
      logo: 'https://images.unsplash.com/photo-1622253692010-333f2da6031d?auto=format&fit=crop&w=50&h=50&q=80',
      number: '01703098052',
      color: 'bg-orange-50 border-orange-200 text-orange-600'
    },
    {
      id: 'rocket',
      name: 'Rocket',
      logo: 'https://images.unsplash.com/photo-1622253692010-333f2da6031d?auto=format&fit=crop&w=50&h=50&q=80',
      number: '01703098052',
      color: 'bg-purple-50 border-purple-200 text-purple-600'
    }
  ];

  return (
    <div className="min-h-screen bg-gradient-to-b from-blue-50 to-white">
      <div className="container mx-auto px-4 py-8">
        <div className="flex items-center mb-8">
          <Link 
            to={`/appointment/${type}/${specialty}/doctor/${id}/book`} 
            className="flex items-center text-gray-600 hover:text-gray-800"
          >
            <ArrowLeft className="w-5 h-5 mr-2" />
            Back
          </Link>
        </div>

        <div className="max-w-4xl mx-auto">
          {/* Payment Summary Card */}
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
                <div className="flex items-center gap-4 mt-2">
                  <span className="bg-green-100 text-green-600 px-3 py-1 rounded-full text-sm">
                    Appointment Scheduled
                  </span>
                  <span className="text-gray-500 text-sm">
                    March 20, 2024 • 10:00 AM
                  </span>
                </div>
              </div>
            </div>

            <div className="grid grid-cols-3 gap-6 py-6">
              <div className="text-center">
                <div className="text-3xl font-bold text-blue-600 mb-1">৳500</div>
                <div className="text-gray-600">Consultation Fee</div>
              </div>
              <div className="text-center border-x border-gray-100">
                <div className="text-3xl font-bold text-green-600 mb-1">30</div>
                <div className="text-gray-600">Minutes Duration</div>
              </div>
              <div className="text-center">
                <div className="text-3xl font-bold text-purple-600 mb-1">24/7</div>
                <div className="text-gray-600">Support Available</div>
              </div>
            </div>
          </div>

          {/* Payment Form */}
          <div className="bg-white rounded-2xl shadow-lg p-8">
            <h2 className="text-2xl font-bold mb-8 text-center">Complete Your Payment</h2>

            <form onSubmit={handleSubmit} className="space-y-8">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-4">
                  Select Payment Method <span className="text-red-500">*</span>
                </label>
                <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                  {paymentMethods.map((method) => (
                    <button
                      key={method.id}
                      type="button"
                      className={`p-4 rounded-xl border-2 transition-all ${
                        paymentMethod === method.id
                          ? method.color
                          : 'border-gray-200 hover:border-gray-300'
                      }`}
                      onClick={() => setPaymentMethod(method.id)}
                    >
                      <div className="flex items-center gap-3 mb-2">
                        <img
                          src={method.logo}
                          alt={method.name}
                          className="w-8 h-8 rounded-lg"
                        />
                        <span className="font-medium">{method.name}</span>
                      </div>
                      <div className="text-sm text-gray-500">
                        Number: {method.number}
                      </div>
                    </button>
                  ))}
                </div>
              </div>

              <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Your Phone Number <span className="text-red-500">*</span>
                  </label>
                  <div className="relative">
                    <Phone className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
                    <input 
                      type="tel"
                      placeholder="Enter your phone number"
                      className="pl-10 w-full p-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-blue-200 shadow-sm"
                      value={phone}
                      onChange={(e) => setPhone(e.target.value)}
                      required
                    />
                  </div>
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Transaction ID <span className="text-red-500">*</span>
                  </label>
                  <div className="relative">
                    <CreditCard className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
                    <input 
                      type="text"
                      placeholder="Enter transaction ID"
                      className="pl-10 w-full p-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-blue-200 shadow-sm"
                      value={transactionId}
                      onChange={(e) => setTransactionId(e.target.value)}
                      required
                    />
                  </div>
                </div>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Payment Screenshot
                </label>
                <label 
                  htmlFor="screenshot-upload" 
                  className="border-2 border-dashed border-gray-300 rounded-xl p-8 flex flex-col items-center justify-center cursor-pointer hover:border-blue-300 transition-colors bg-gray-50"
                >
                  <FileUp size={32} className="text-gray-400 mb-4" />
                  <p className="text-sm text-gray-500 mb-1">Click to upload or drag and drop</p>
                  <p className="text-xs text-gray-400">JPG/PNG Format Only (Max 5MB)</p>
                  <input
                    id="screenshot-upload"
                    type="file"
                    className="hidden"
                    accept=".jpg,.jpeg,.png"
                    onChange={(e) => setScreenshot(e.files?.[0] || null)}
                  />
                </label>
                {screenshot && (
                  <p className="mt-2 text-sm text-green-600 flex items-center gap-1">
                    <CheckCircle size={16} />
                    File selected: {screenshot.name}
                  </p>
                )}
              </div>

              <div className="bg-blue-50 rounded-xl p-6">
                <div className="flex items-center gap-2 mb-4">
                  <Shield className="text-blue-500" size={20} />
                  <h3 className="font-semibold">Secure Payment</h3>
                </div>
                <div className="flex items-start gap-2 text-sm text-gray-600">
                  <AlertCircle className="text-blue-500 flex-shrink-0" size={16} />
                  <p>
                    Please make sure to double-check the payment details before submitting. 
                    Keep your transaction ID safe for future reference.
                  </p>
                </div>
              </div>

              <button
                type="submit"
                className="w-full py-4 bg-gradient-to-r from-blue-500 to-blue-600 text-white rounded-xl hover:from-blue-600 hover:to-blue-700 transition-all shadow-lg hover:shadow-xl font-medium text-lg flex items-center justify-center gap-2"
              >
                <CreditCard size={20} />
                Complete Payment
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}