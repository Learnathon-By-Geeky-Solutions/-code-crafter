import { useState } from 'react';
import { Link } from 'react-router-dom';
import { Search, ShoppingCart, Plus, Minus, Trash2 } from 'lucide-react';

interface Medicine {
  id: string;
  name: string;
  manufacturer: string;
  price: number;
  description: string;
  image: string;
}

interface CartItem {
  medicine: Medicine;
  quantity: number;
}

const medicines: Medicine[] = [
  {
    id: '1',
    name: 'Paracetamol 25mg',
    manufacturer: 'Eskayef Pharmaceuticals',
    price: 64,
    description: 'Syrup for pain relief treatment',
    image: 'https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?auto=format&fit=crop&w=200&h=200'
  },
  {
    id: '2',
    name: 'Amoxicillin 250mg',
    manufacturer: 'Square Pharmaceuticals',
    price: 117,
    description: 'Cream for antibiotics treatment',
    image: 'https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?auto=format&fit=crop&w=200&h=200'
  }
];

const pharmacies = [
  {
    id: '1',
    name: 'HealthFirst Pharmacy',
    address: '123 Medical Center Road, Dhaka',
    phone: '+880 1234-567890',
    rating: 4.8,
    openingHours: '24/7',
    image: 'https://images.unsplash.com/photo-1576091160550-2173dba999ef?auto=format&fit=crop&w=300&h=200'
  },
  {
    id: '2',
    name: 'City Pharmacy',
    address: '456 Hospital Zone, Chittagong',
    phone: '+880 1234-567891',
    rating: 4.6,
    openingHours: '24/7',
    image: 'https://images.unsplash.com/photo-1587351021759-3e566b6af7cc?auto=format&fit=crop&w=300&h=200'
  }
];

export function MedicineSection() {
  const [searchType, setSearchType] = useState<'medicine' | 'pharmacy'>('medicine');
  const [searchTerm, setSearchTerm] = useState('');
  const [cart, setCart] = useState<CartItem[]>([]);
  const [showResults, setShowResults] = useState(false);
  const [selectedDivision, setSelectedDivision] = useState('');
  const [selectedDistrict, setSelectedDistrict] = useState('');
  const [selectedCity, setSelectedCity] = useState('');

  const divisions = [
    'Dhaka',
    'Chittagong',
    'Rajshahi',
    'Khulna',
    'Barisal',
    'Sylhet',
    'Rangpur',
    'Mymensingh'
  ];

  const districts = {
    'Dhaka': ['Dhaka', 'Gazipur', 'Narayanganj'],
    'Chittagong': ['Chittagong', "Cox's Bazar", 'Comilla']
  };

  const cities = {
    'Dhaka': ['Uttara', 'Gulshan', 'Mirpur', 'Dhanmondi'],
    'Gazipur': ['Gazipur Sadar', 'Tongi', 'Sreepur']
  };

  const filteredMedicines = medicines.filter(medicine =>
    medicine.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
    medicine.manufacturer.toLowerCase().includes(searchTerm.toLowerCase()) ||
    medicine.description.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const filteredPharmacies = pharmacies.filter(pharmacy =>
    pharmacy.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
    pharmacy.address.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const addToCart = (medicine: Medicine) => {
    setCart(prevCart => {
      const existingItem = prevCart.find(item => item.medicine.id === medicine.id);
      if (existingItem) {
        return prevCart.map(item =>
          item.medicine.id === medicine.id
            ? { ...item, quantity: item.quantity + 1 }
            : item
        );
      }
      return [...prevCart, { medicine, quantity: 1 }];
    });
  };

  const updateQuantity = (medicineId: string, newQuantity: number) => {
    if (newQuantity < 1) {
      setCart(prevCart => prevCart.filter(item => item.medicine.id !== medicineId));
    } else {
      setCart(prevCart =>
        prevCart.map(item =>
          item.medicine.id === medicineId
            ? { ...item, quantity: newQuantity }
            : item
        )
      );
    }
  };

  const removeFromCart = (medicineId: string) => {
    setCart(prevCart => prevCart.filter(item => item.medicine.id !== medicineId));
  };

  const subtotal = cart.reduce((sum, item) => sum + (item.medicine.price * item.quantity), 0);

  return (
    <div className="py-16 px-6 md:px-12 bg-gray-50">
      <div className="max-w-6xl mx-auto">
        <div className="text-center mb-12">
          <h2 className="text-3xl font-bold mb-4">Online Medicine Services</h2>
          <p className="text-gray-600 max-w-2xl mx-auto">
            Find and order medicines from verified pharmacies near you. We ensure authentic medicines and timely delivery.
          </p>
        </div>

        <div className="flex justify-center gap-4 mb-8">
          <button
            className={`px-6 py-3 rounded-full flex items-center gap-2 ${
              searchType === 'medicine'
                ? 'bg-blue-500 text-white'
                : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
            }`}
            onClick={() => {
              setSearchType('medicine');
              setShowResults(true);
            }}
          >
            <ShoppingCart size={20} />
            Search Medicines
          </button>
          <button
            className={`px-6 py-3 rounded-full flex items-center gap-2 ${
              searchType === 'pharmacy'
                ? 'bg-blue-500 text-white'
                : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
            }`}
            onClick={() => {
              setSearchType('pharmacy');
              setShowResults(true);
            }}
          >
            Find Pharmacies
          </button>
        </div>

        <div className="bg-white rounded-xl shadow-lg p-6 mb-8">
          <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
            <div className="relative">
              <Search className="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
              <input
                type="text"
                placeholder={searchType === 'medicine' ? "Search medicines..." : "Search pharmacies..."}
                className="w-full pl-12 pr-4 py-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                value={searchTerm}
                onChange={(e) => {
                  setSearchTerm(e.target.value);
                  setShowResults(true);
                }}
              />
            </div>

            <select
              className="p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
              value={selectedDivision}
              onChange={(e) => setSelectedDivision(e.target.value)}
            >
              <option value="">Select Division</option>
              {divisions.map(division => (
                <option key={division} value={division}>{division}</option>
              ))}
            </select>

            <select
              className="p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
              value={selectedDistrict}
              onChange={(e) => setSelectedDistrict(e.target.value)}
              disabled={!selectedDivision}
            >
              <option value="">Select District</option>
              {selectedDivision && districts[selectedDivision as keyof typeof districts]?.map(district => (
                <option key={district} value={district}>{district}</option>
              ))}
            </select>

            <select
              className="p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
              value={selectedCity}
              onChange={(e) => setSelectedCity(e.target.value)}
              disabled={!selectedDistrict}
            >
              <option value="">Select City</option>
              {selectedDistrict && cities[selectedDistrict as keyof typeof cities]?.map(city => (
                <option key={city} value={city}>{city}</option>
              ))}
            </select>
          </div>
        </div>

        {showResults && (
          <div className={`flex ${searchType === 'medicine' ? 'gap-6' : ''}`}>
            <div className={searchType === 'medicine' ? 'w-3/4' : 'w-full'}>
              <div className={`grid grid-cols-1 ${searchType === 'medicine' ? 'md:grid-cols-2' : 'md:grid-cols-3'} gap-6`}>
                {searchType === 'medicine' ? (
                  filteredMedicines.map(medicine => (
                    <div key={medicine.id} className="bg-white rounded-lg shadow-sm p-4">
                      <div className="flex items-start gap-4">
                        <img
                          src={medicine.image}
                          alt={medicine.name}
                          className="w-20 h-20 object-cover rounded-lg"
                        />
                        <div className="flex-1">
                          <h3 className="font-semibold mb-1">{medicine.name}</h3>
                          <p className="text-sm text-gray-600 mb-1">{medicine.manufacturer}</p>
                          <p className="text-sm text-gray-600 mb-4">{medicine.description}</p>
                          <div className="text-lg font-semibold mb-3">৳{medicine.price}</div>
                          <button
                            onClick={() => addToCart(medicine)}
                            className="w-full py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors flex items-center justify-center gap-2"
                          >
                            <ShoppingCart size={16} />
                            Add to Cart
                          </button>
                        </div>
                      </div>
                    </div>
                  ))
                ) : (
                  filteredPharmacies.map(pharmacy => (
                    <div key={pharmacy.id} className="bg-white rounded-lg shadow-sm overflow-hidden">
                      <img
                        src={pharmacy.image}
                        alt={pharmacy.name}
                        className="w-full h-40 object-cover"
                      />
                      <div className="p-4">
                        <h3 className="font-semibold mb-2">{pharmacy.name}</h3>
                        <p className="text-sm text-gray-600 mb-2">{pharmacy.address}</p>
                        <p className="text-sm text-gray-600 mb-2">Phone: {pharmacy.phone}</p>
                        <p className="text-sm text-gray-600">Hours: {pharmacy.openingHours}</p>
                        <div className="mt-4">
                          <button
                            onClick={() => window.location.href = `tel:${pharmacy.phone}`}
                            className="w-full py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 transition-colors"
                          >
                            Call Now
                          </button>
                        </div>
                      </div>
                    </div>
                  ))
                )}
              </div>
            </div>

            {searchType === 'medicine' && cart.length > 0 && (
              <div className="w-1/4">
                <div className="bg-white rounded-lg shadow-sm p-6 sticky top-4">
                  <h2 className="text-xl font-semibold mb-6">Shopping Cart</h2>
                  <div className="space-y-4">
                    {cart.map((item) => (
                      <div key={item.medicine.id} className="flex gap-4 border-b pb-4">
                        <img
                          src={item.medicine.image}
                          alt={item.medicine.name}
                          className="w-16 h-16 object-cover rounded-lg"
                        />
                        <div className="flex-grow">
                          <h3 className="font-medium">{item.medicine.name}</h3>
                          <p className="text-sm text-gray-600">৳{item.medicine.price}</p>
                          <div className="flex items-center gap-2 mt-2">
                            <button
                              onClick={() => updateQuantity(item.medicine.id, item.quantity - 1)}
                              className="p-1 hover:bg-gray-100 rounded"
                            >
                              <Minus size={16} />
                            </button>
                            <span className="w-8 text-center">{item.quantity}</span>
                            <button
                              onClick={() => updateQuantity(item.medicine.id, item.quantity + 1)}
                              className="p-1 hover:bg-gray-100 rounded"
                            >
                              <Plus size={16} />
                            </button>
                            <button
                              onClick={() => removeFromCart(item.medicine.id)}
                              className="ml-auto text-red-500 hover:text-red-600"
                            >
                              <Trash2 size={16} />
                            </button>
                          </div>
                        </div>
                      </div>
                    ))}
                  </div>

                  <div className="mt-6 pt-4 border-t">
                    <div className="flex justify-between font-semibold text-lg">
                      <span>Total</span>
                      <span>৳{subtotal}</span>
                    </div>
                    <Link
                      to="/medicine/checkout"
                      className="block w-full text-center bg-blue-500 text-white py-3 rounded-lg mt-4 hover:bg-blue-600 transition-colors"
                    >
                      Proceed to Checkout
                    </Link>
                  </div>
                </div>
              </div>
            )}
          </div>
        )}

        <div className="text-center mt-8">
          <Link
            to="/medicine"
            className="inline-block px-6 py-3 bg-blue-500 text-white rounded-full hover:bg-blue-600 transition-colors"
          >
            Visit Medicine Store
          </Link>
        </div>
      </div>
    </div>
  );
}