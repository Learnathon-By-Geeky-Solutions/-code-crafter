import { useState } from 'react';
import { Search } from 'lucide-react';

interface Medicine {
  id: string;
  name: string;
  manufacturer: string;
  price: number;
  inStock: boolean;
  description: string;
  category: string;
  image: string;
}

const MOCK_MEDICINES: Medicine[] = [
  {
    id: '1',
    name: 'Paracetamol 500mg',
    manufacturer: 'Healthcare Pharma',
    price: 50,
    inStock: true,
    description: 'Pain reliever and fever reducer',
    category: 'Pain Relief',
    image: 'https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?auto=format&fit=crop&w=200&h=200'
  },
  {
    id: '2',
    name: 'Amoxicillin 500mg',
    manufacturer: 'MediCare Ltd',
    price: 120,
    inStock: true,
    description: 'Antibiotic for bacterial infections',
    category: 'Antibiotics',
    image: 'https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?auto=format&fit=crop&w=200&h=200'
  }
];

export function MedicineSearchPage() {
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('all');

  const categories = ['All', 'Pain Relief', 'Antibiotics', 'Digestive Health'];

  const filteredMedicines = MOCK_MEDICINES.filter(medicine => {
    const matchesSearch = medicine.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      medicine.manufacturer.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesCategory = selectedCategory.toLowerCase() === 'all' || medicine.category === selectedCategory;
    return matchesSearch && matchesCategory;
  });

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-6xl mx-auto">
        <h1 className="text-3xl font-bold mb-8">Search Medicines</h1>

        <div className="bg-white rounded-lg shadow-sm p-6">
          <div className="flex flex-col md:flex-row md:items-center justify-between gap-4 mb-8">
            <div className="relative flex-1">
              <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
              <input
                type="text"
                placeholder="Search medicines by name or manufacturer..."
                className="w-full pl-10 pr-4 py-2 rounded-lg border-gray-200 focus:ring-blue-500 focus:border-blue-500"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </div>
            <div className="flex gap-2">
              {categories.map((category) => (
                <button
                  key={category}
                  className={'px-4 py-2 rounded-full ' + (
                    selectedCategory === category
                      ? 'bg-blue-500 text-white'
                      : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
                  )}
                  onClick={() => setSelectedCategory(category)}
                >
                  {category}
                </button>
              ))}
            </div>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {filteredMedicines.map((medicine) => (
              <div key={medicine.id} className="border rounded-lg p-4">
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
                      <span className="font-semibold text-lg">৳{medicine.price}</span>
                      <button
                        className={'px-3 py-1 rounded-full flex items-center gap-1 ' + (
                          medicine.inStock
                            ? 'bg-blue-500 text-white hover:bg-blue-600'
                            : 'bg-gray-100 text-gray-400 cursor-not-allowed'
                        )}
                        disabled={!medicine.inStock}
                      >
                        Find in Pharmacy
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}