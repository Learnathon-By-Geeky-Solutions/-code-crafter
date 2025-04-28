import { useState } from 'react';
import { Link } from 'react-router-dom';
import { Search, MapPin, Filter, Pill, Building2, Star, Clock, Phone, ShoppingCart, Plus, Minus, X, Trash2 } from 'lucide-react';

interface Medicine {
  id: string;
  name: string;
  manufacturer: string;
  price: number;
  inStock: boolean;
  description: string;
  category: string;
  image: string;
  dosageForm: string;
  strength: string;
  prescriptionRequired: boolean;
}

interface Pharmacy {
  id: string;
  name: string;
  address: string;
  phone: string;
  rating: number;
  openingHours: string;
  image: string;
  division: string;
  district: string;
  city: string;
  features: string[];
  established: number;
  deliveryAvailable: boolean;
  emergencyService: boolean;
}

interface CartItem {
  medicine: Medicine;
  quantity: number;
}

const generateMedicines = (): Medicine[] => {
  const categories = [
    'Pain Relief',
    'Antibiotics',
    'Cardiovascular',
    'Diabetes',
    'Respiratory',
    'Gastrointestinal',
    'Mental Health',
    'Allergy',
    'Vitamins & Supplements',
    'Dermatology'
  ];

  const manufacturers = [
    'Square Pharmaceuticals',
    'Incepta Pharmaceuticals',
    'Beximco Pharma',
    'Healthcare Pharma',
    'ACME Laboratories',
    'Renata Limited',
    'Drug International',
    'ACI Limited',
    'Eskayef Pharmaceuticals',
    'Opsonin Pharma'
  ];

  const dosageForms = [
    'Tablet',
    'Capsule',
    'Syrup',
    'Injection',
    'Cream',
    'Ointment',
    'Gel',
    'Spray',
    'Drops',
    'Powder'
  ];

  const medicines: Medicine[] = [];
  const medicineData = [
    { name: 'Paracetamol', category: 'Pain Relief', basePrice: 50 },
    { name: 'Amoxicillin', category: 'Antibiotics', basePrice: 120 },
    { name: 'Omeprazole', category: 'Gastrointestinal', basePrice: 80 },
    { name: 'Metformin', category: 'Diabetes', basePrice: 90 },
    { name: 'Amlodipine', category: 'Cardiovascular', basePrice: 150 },
    { name: 'Salbutamol', category: 'Respiratory', basePrice: 100 },
    { name: 'Fluoxetine', category: 'Mental Health', basePrice: 200 },
    { name: 'Cetirizine', category: 'Allergy', basePrice: 70 },
    { name: 'Vitamin D3', category: 'Vitamins & Supplements', basePrice: 60 },
    { name: 'Betamethasone', category: 'Dermatology', basePrice: 110 }
  ];

  const strengths = ['250mg', '500mg', '1000mg', '5mg', '10mg', '20mg', '25mg', '50mg', '100mg'];

  for (let i = 0; i < 150; i++) {
    const medicineTemplate = medicineData[i % medicineData.length];
    const manufacturer = manufacturers[Math.floor(Math.random() * manufacturers.length)];
    const dosageForm = dosageForms[Math.floor(Math.random() * dosageForms.length)];
    const strength = strengths[Math.floor(Math.random() * strengths.length)];
    const priceVariation = Math.random() * 40 - 20;

    medicines.push({
      id: `med-${i + 1}`,
      name: `${medicineTemplate.name} ${strength}`,
      manufacturer,
      price: Math.round(medicineTemplate.basePrice + priceVariation),
      inStock: Math.random() > 0.1,
      description: `${dosageForm} for ${medicineTemplate.category.toLowerCase()} treatment`,
      category: medicineTemplate.category,
      image: 'https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?auto=format&fit=crop&w=200&h=200',
      dosageForm,
      strength,
      prescriptionRequired: Math.random() > 0.5
    });
  }

  return medicines;
};

const generatePharmacies = (): Pharmacy[] => {
  const pharmacyNames = [
    'HealthFirst Pharmacy',
    'MediCare Plus',
    'Wellness Pharma',
    'LifeCare Pharmacy',
    'Family Health Pharmacy',
    'Green Cross Pharmacy',
    'Modern Medicine Center',
    'Care & Cure Pharmacy',
    'City Pharmacy',
    'Union Pharmacy'
  ];

  const features = [
    '24/7 Service',
    'Home Delivery',
    'Online Prescription',
    'Vaccination Service',
    'Health Checkup',
    'Medicine Counseling',
    'Baby Care Products',
    'Diabetes Care',
    'Personal Care',
    'Health Supplements'
  ];

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
    'Chittagong': ['Chittagong', "Cox's Bazar", 'Comilla'],
    'Rajshahi': ['Rajshahi', 'Bogra', 'Pabna'],
    'Khulna': ['Khulna', 'Jessore', 'Kushtia'],
    'Barisal': ['Barisal', 'Bhola', 'Patuakhali'],
    'Sylhet': ['Sylhet', 'Moulvibazar', 'Habiganj'],
    'Rangpur': ['Rangpur', 'Dinajpur', 'Kurigram'],
    'Mymensingh': ['Mymensingh', 'Jamalpur', 'Netrokona']
  };

  const cities = {
    'Dhaka': ['Uttara', 'Gulshan', 'Mirpur', 'Dhanmondi', 'Banani', 'Mohammadpur'],
    'Gazipur': ['Gazipur Sadar', 'Tongi', 'Sreepur'],
    'Chittagong': ['Agrabad', 'Nasirabad', 'Halishahar', 'GEC Circle']
  };

  const pharmacies: Pharmacy[] = [];

  for (let i = 0; i < 50; i++) {
    const division = divisions[Math.floor(Math.random() * divisions.length)];
    const district = districts[division as keyof typeof districts][Math.floor(Math.random() * districts[division as keyof typeof districts].length)];
    const city = cities[district as keyof typeof cities]?.[Math.floor(Math.random() * (cities[district as keyof typeof cities]?.length || 1))] || district;

    const randomFeatures = [...features]
      .sort(() => Math.random() - 0.5)
      .slice(0, Math.floor(Math.random() * 5) + 3);

    const basePharmacyName = pharmacyNames[i % pharmacyNames.length];
    const pharmacyName = i < pharmacyNames.length 
      ? basePharmacyName 
      : `${basePharmacyName} ${Math.floor(i / pharmacyNames.length) + 1}`;

    pharmacies.push({
      id: `pharm-${i + 1}`,
      name: pharmacyName,
      address: `${Math.floor(Math.random() * 200) + 1}, ${city}, ${district}`,
      phone: `+880 ${Math.floor(Math.random() * 2) + 1}${Math.random().toString().slice(2, 11)}`,
      rating: Number((Math.random() * (5 - 3.5) + 3.5).toFixed(1)),
      openingHours: Math.random() > 0.3 ? '24/7' : '9:00 AM - 11:00 PM',
      image: 'https://images.unsplash.com/photo-1576091160550-2173dba999ef?auto=format&fit=crop&w=300&h=200',
      division,
      district,
      city,
      features: randomFeatures,
      established: Math.floor(Math.random() * (2023 - 1990) + 1990),
      deliveryAvailable: Math.random() > 0.2,
      emergencyService: Math.random() > 0.4
    });
  }

  return pharmacies;
};

const medicines = generateMedicines();
const pharmacies = generatePharmacies();

export default function MedicinePage() {
  const [searchType, setSearchType] = useState<'medicine' | 'pharmacy'>('medicine');
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('all');
  const [selectedLocation, setSelectedLocation] = useState({
    division: '',
    district: '',
    city: ''
  });
  const [cart, setCart] = useState<CartItem[]>([]);
  const [isCartOpen, setIsCartOpen] = useState(false);
  const [couponCode, setCouponCode] = useState('');

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

  const categories = Array.from(new Set(medicines.map(med => med.category)));

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
  const discount = couponCode === 'SAVE10' ? subtotal * 0.1 : 0;
  const total = subtotal - discount;

  const filteredMedicines = medicines.filter(medicine => {
    const matchesSearch = 
      medicine.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      medicine.manufacturer.toLowerCase().includes(searchTerm.toLowerCase()) ||
      medicine.description.toLowerCase().includes(searchTerm.toLowerCase());
    
    const matchesCategory = selectedCategory === 'all' || medicine.category === selectedCategory;

    return matchesSearch && matchesCategory;
  });

  const filteredPharmacies = pharmacies.filter(pharmacy => {
    const matchesSearch = 
      pharmacy.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      pharmacy.address.toLowerCase().includes(searchTerm.toLowerCase());

    const matchesLocation = (
      (!selectedLocation.division || pharmacy.division === selectedLocation.division) &&
      (!selectedLocation.district || pharmacy.district === selectedLocation.district) &&
      (!selectedLocation.city || pharmacy.city === selectedLocation.city)
    );

    return matchesSearch && matchesLocation;
  });

  const handleCall = (phone: string) => {
    window.location.href = `tel:${phone}`;
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-7xl mx-auto">
        <div className="text-center mb-12">
          <h1 className="text-4xl font-bold text-gray-900 mb-4">Online Medicine Services</h1>
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
            onClick={() => setSearchType('medicine')}
          >
            <Pill size={20} />
            Search Medicines
          </button>
          <button
            className={`px-6 py-3 rounded-full flex items-center gap-2 ${
              searchType === 'pharmacy'
                ? 'bg-green-500 text-white'
                : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
            }`}
            onClick={() => setSearchType('pharmacy')}
          >
            <Building2 size={20} />
            Find Pharmacies
          </button>
        </div>

        <div className="bg-white rounded-xl shadow-lg p-6 mb-12">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div className="relative">
              <Search className="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
              <input
                type="text"
                placeholder={searchType === 'medicine' ? "Search medicines..." : "Search pharmacies..."}
                className="w-full pl-12 pr-4 py-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </div>

            <div className="flex gap-4">
              <select
                className="flex-1 p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                value={selectedLocation.division}
                onChange={(e) => setSelectedLocation({
                  ...selectedLocation,
                  division: e.target.value,
                  district: '',
                  city: ''
                })}
              >
                <option value="">Select Division</option>
                {divisions.map(division => (
                  <option key={division} value={division}>{division}</option>
                ))}
              </select>

              <select
                className="flex-1 p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                value={selectedLocation.district}
                onChange={(e) => setSelectedLocation({
                  ...selectedLocation,
                  district: e.target.value,
                  city: ''
                })}
                disabled={!selectedLocation.division}
              >
                <option value="">Select District</option>
                {selectedLocation.division && districts[selectedLocation.division as keyof typeof districts]?.map(district => (
                  <option key={district} value={district}>{district}</option>
                ))}
              </select>

              <select
                className="flex-1 p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                value={selectedLocation.city}
                onChange={(e) => setSelectedLocation({
                  ...selectedLocation,
                  city: e.target.value
                })}
                disabled={!selectedLocation.district}
              >
                <option value="">Select City</option>
                {selectedLocation.district && cities[selectedLocation.district as keyof typeof cities]?.map(city => (
                  <option key={city} value={city}>{city}</option>
                ))}
              </select>
            </div>
          </div>
        </div>

        <div className={`flex ${searchType === 'medicine' ? 'gap-6' : ''}`}>
          <div className={searchType === 'medicine' ? 'w-3/4' : 'w-full'}>
            <div className={`grid grid-cols-1 ${
              searchType === 'medicine' 
                ? 'md:grid-cols-2' 
                : 'md:grid-cols-3'
            } gap-6`}>
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
                        <p className="text-sm text-gray-600 mb-2">{medicine.description}</p>
                        <div className="flex items-center justify-between">
                          <span className="font-semibold">৳{medicine.price}</span>
                        </div>
                        {medicine.inStock ? (
                          <button
                            onClick={() => addToCart(medicine)}
                            className="mt-3 w-full py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors flex items-center justify-center gap-2"
                          >
                            <ShoppingCart size={16} />
                            Add to Cart
                          </button>
                        ) : (
                          <span className="mt-3 inline-block px-2 py-1 bg-red-100 text-red-600 rounded-full text-sm">
                            Out of Stock
                          </span>
                        )}
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
                      <div className="flex items-center justify-between mb-2">
                        <h3 className="font-semibold">{pharmacy.name}</h3>
                        <div className="flex items-center gap-1">
                          <Star size={16} className="text-yellow-500 fill-current" />
                          <span>{pharmacy.rating}</span>
                        </div>
                      </div>
                      <div className="space-y-2 text-sm text-gray-600">
                        <div className="flex items-center gap-2">
                          <MapPin size={16} />
                          <span>{pharmacy.address}</span>
                        </div>
                        <div className="flex items-center gap-2">
                          <Phone size={16} />
                          <span>{pharmacy.phone}</span>
                        </div>
                        <div className="flex items-center gap-2">
                          <Clock size={16} />
                          <span>{pharmacy.openingHours}</span>
                        </div>
                      </div>
                      <div className="mt-3 flex flex-wrap gap-2">
                        {pharmacy.features.map((feature, index) => (
                          <span
                            key={index}
                            className="px-2 py-1 bg-blue-50 text-blue-600 rounded-full text-xs"
                          >
                            {feature}
                          </span>
                        ))}
                      </div>
                      {pharmacy.emergencyService && (
                        <div className="mt-3">
                          <span className="px-2 py-1 bg-red-100 text-red-600 rounded-full text-xs">
                            Emergency Service Available
                          </span>
                        </div>
                      )}
                      <button
                        onClick={() => handleCall(pharmacy.phone)}
                        className="mt-4 w-full py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 transition-colors flex items-center justify-center gap-2"
                      >
                        <Phone size={16} />
                        Call Now
                      </button>
                    </div>
                  </div>
                ))
              )}
            </div>
          </div>

          {searchType === 'medicine' && (
            <div className="w-1/4 bg-white rounded-lg shadow-sm p-6 h-fit sticky top-4">
              <h2 className="text-xl font-semibold mb-6">Shopping Cart</h2>

              {cart.length === 0 ? (
                <div className="text-center py-8 text-gray-500">
                  Your cart is empty
                </div>
              ) : (
                <>
                  <div className="space-y-4 max-h-[400px] overflow-y-auto">
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

                  <div className="border-t pt-4 mt-4">
                    <div className="mb-4">
                      <label className="block text-sm font-medium text-gray-700 mb-2">
                        Apply Coupon
                      </label>
                      <div className="flex gap-2">
                        <input
                          type="text"
                          className="flex-grow p-2 border rounded-lg"
                          value={couponCode}
                          onChange={(e) => setCouponCode(e.target.value)}
                          placeholder="Enter coupon code"
                        />
                        <button
                          className="px-4 py-2 bg-gray-100 text-gray-700 rounded-lg hover:bg-gray-200"
                          onClick={() => {/* Handle coupon validation */}}
                        >
                          Apply
                        </button>
                      </div>
                      {couponCode === 'SAVE10' && (
                        <p className="text-green-500 text-sm mt-1">10% discount applied!</p>
                      )}
                    </div>

                    <div className="space-y-2 text-sm">
                      <div className="flex justify-between">
                        <span>Subtotal</span>
                        <span>৳{subtotal.toFixed(2)}</span>
                      </div>
                      {discount > 0 && (
                        <div className="flex justify-between text-green-500">
                          <span>Discount</span>
                          <span>-৳{discount.toFixed(2)}</span>
                        </div>
                      )}
                      <div className="flex justify-between font-semibold text-lg pt-2 border-t">
                        <span>Total</span>
                        <span>৳{total.toFixed(2)}</span>
                      </div>
                    </div>

                    <Link
                      to="/medicine/checkout"
                      className="block w-full text-center bg-blue-500 text-white py-3 rounded-lg mt-4 hover:bg-blue-600 transition-colors"
                    >
                      Proceed to Checkout
                    </Link>
                  </div>
                </>
              )}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export { MedicinePage }