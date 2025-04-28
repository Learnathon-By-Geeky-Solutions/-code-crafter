import { useState } from 'react';
import { ArrowLeft, Calendar, MapPin, User } from 'lucide-react';
import { Link } from 'react-router-dom';

interface DonationRecord {
  id: string;
  recipient: string;
  date: string;
  location: string;
  bloodGroup: string;
  units: number;
  certificate: string;
}

const donationHistory: DonationRecord[] = [
  {
    id: '1',
    recipient: 'John Doe',
    date: '2024-03-15',
    location: 'City General Hospital',
    bloodGroup: 'A+',
    units: 1,
    certificate: 'https://example.com/certificate1'
  },
  {
    id: '2',
    recipient: 'Jane Smith',
    date: '2024-02-01',
    location: 'Red Cross Blood Bank',
    bloodGroup: 'A+',
    units: 1,
    certificate: 'https://example.com/certificate2'
  }
];

export function BloodDonationHistoryPage() {
  const [filter, setFilter] = useState('all');

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="flex items-center mb-8">
        <Link to="/blood-donation" className="flex items-center text-gray-600 hover:text-gray-800">
          <ArrowLeft className="w-5 h-5 mr-2" />
          Back
        </Link>
      </div>

      <div className="max-w-4xl mx-auto">
        <div className="flex justify-between items-center mb-8">
          <h1 className="text-2xl font-bold">Blood Donation History</h1>
          <select
            className="p-2 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
            value={filter}
            onChange={(e) => setFilter(e.target.value)}
          >
            <option value="all">All Time</option>
            <option value="month">This Month</option>
            <option value="year">This Year</option>
          </select>
        </div>

        <div className="bg-white rounded-lg shadow-sm p-6">
          <div className="grid grid-cols-3 gap-4 mb-8">
            <div className="bg-red-50 p-4 rounded-lg">
              <h3 className="text-lg font-semibold mb-1">Total Donations</h3>
              <p className="text-3xl font-bold text-red-600">2</p>
            </div>
            <div className="bg-blue-50 p-4 rounded-lg">
              <h3 className="text-lg font-semibold mb-1">Total Units</h3>
              <p className="text-3xl font-bold text-blue-600">2</p>
            </div>
            <div className="bg-green-50 p-4 rounded-lg">
              <h3 className="text-lg font-semibold mb-1">Lives Saved</h3>
              <p className="text-3xl font-bold text-green-600">6</p>
            </div>
          </div>

          <div className="space-y-4">
            {donationHistory.map((record) => (
              <div key={record.id} className="border rounded-lg p-4">
                <div className="flex justify-between items-start mb-4">
                  <div>
                    <div className="flex items-center gap-2 mb-2">
                      <User size={18} className="text-gray-500" />
                      <span className="font-medium">{record.recipient}</span>
                    </div>
                    <div className="flex items-center gap-2 text-sm text-gray-600">
                      <Calendar size={16} />
                      <span>{new Date(record.date).toLocaleDateString()}</span>
                    </div>
                    <div className="flex items-center gap-2 text-sm text-gray-600 mt-1">
                      <MapPin size={16} />
                      <span>{record.location}</span>
                    </div>
                  </div>
                  <div className="text-right">
                    <span className="bg-red-100 text-red-600 px-3 py-1 rounded-full text-sm">
                      {record.bloodGroup}
                    </span>
                    <p className="text-sm text-gray-600 mt-2">
                      {record.units} unit{record.units > 1 ? 's' : ''}
                    </p>
                  </div>
                </div>
                <a
                  href={record.certificate}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="text-blue-500 text-sm hover:text-blue-600"
                >
                  View Certificate →
                </a>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}