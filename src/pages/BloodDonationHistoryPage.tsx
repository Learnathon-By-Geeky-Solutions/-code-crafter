import { useEffect, useState } from 'react';
import { ArrowLeft, Calendar, MapPin, User, Activity, Heart } from 'lucide-react';
import { Link } from 'react-router-dom';
import { api } from '../lib/api';

interface DonationRecord {
  recipient: string;
  date: string;
  location: string;
  bloodGroup: string;
  units: number;
  certificate?: string;
}

interface DonorProfile {
  bloodType: string;
  age: number;
  weight: number;
  interested: 'YES' | 'NO';
  canDonate: boolean;
}

interface DonationHistoryResponse {
  donationCount: number;
  unitCount: number;
  bloodType: string | null;
  bloodDonationHistoryResponses: DonationRecord[] | null;
}

export function BloodDonationHistoryPage() {
  const [profile, setProfile] = useState<DonorProfile | null>(null);
  const [history, setHistory] = useState<DonationHistoryResponse | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchProfileAndHistory();
  }, []);

  const fetchProfileAndHistory = async () => {
    try {
      const [profileRes, historyRes] = await Promise.all([
        api.get('/api/v1/donor/profile'),
        api.get('/api/v1/donor/donation-history')
      ]);

      if (profileRes.data.code === 'XS0001') {
        const d = profileRes.data.data;
        setProfile({
          bloodType: d.bloodType,
          age: d.age,
          weight: d.weight,
          interested: d.interested,
          canDonate: d.canDonate
        });
      }

      if (historyRes.data.code === 'XS0001') {
        setHistory(historyRes.data.data);
      }
    } catch (err) {
      console.error('Failed to load data:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="flex items-center mb-8">
        <Link to="/blood-donation" className="flex items-center text-gray-600 hover:text-gray-800">
          <ArrowLeft className="w-5 h-5 mr-2" />
          Back
        </Link>
      </div>

      <div className="max-w-4xl mx-auto space-y-8">
        {/* Profile Section */}
        <div className="bg-white rounded-lg shadow-sm p-6">
          <h2 className="text-xl font-bold mb-4">Donor Profile</h2>
          {profile ? (
            <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
              <div className="bg-red-50 p-4 rounded-lg text-center">
                <h4 className="text-sm text-gray-500">Blood Type</h4>
                <p className="text-xl font-bold text-red-600">
                  {profile.bloodType.replace('_POS', '+').replace('_NEG', '-')}
                </p>
              </div>
              <div className="bg-blue-50 p-4 rounded-lg text-center">
                <h4 className="text-sm text-gray-500">Age</h4>
                <p className="text-xl font-bold text-blue-600">{profile.age}</p>
              </div>
              <div className="bg-green-50 p-4 rounded-lg text-center">
                <h4 className="text-sm text-gray-500">Weight</h4>
                <p className="text-xl font-bold text-green-600">{profile.weight} kg</p>
              </div>
              <div className="bg-yellow-50 p-4 rounded-lg text-center">
                <h4 className="text-sm text-gray-500">Eligible to Donate</h4>
                <p className="text-xl font-bold text-yellow-600">{profile.canDonate ? 'Yes' : 'No'}</p>
              </div>
            </div>
          ) : (
            <div className="text-gray-500">No profile found.</div>
          )}
        </div>

        {/* Donation Stats */}
        <div className="bg-white rounded-lg shadow-sm p-6">
          <h2 className="text-xl font-bold mb-4">Donation Summary</h2>
          <div className="grid grid-cols-2 md:grid-cols-3 gap-4">
            <div className="bg-red-100 p-4 rounded-lg text-center">
              <Activity className="mx-auto text-red-500" />
              <h4 className="text-sm text-gray-500 mt-2">Total Donations</h4>
              <p className="text-xl font-bold text-red-600">{history?.donationCount ?? 0}</p>
            </div>
            <div className="bg-blue-100 p-4 rounded-lg text-center">
              <Heart className="mx-auto text-blue-500" />
              <h4 className="text-sm text-gray-500 mt-2">Total Units</h4>
              <p className="text-xl font-bold text-blue-600">{history?.unitCount ?? 0}</p>
            </div>
            <div className="bg-green-100 p-4 rounded-lg text-center">
              <User className="mx-auto text-green-500" />
              <h4 className="text-sm text-gray-500 mt-2">Interested</h4>
              <p className="text-xl font-bold text-green-600">{profile?.interested === 'YES' ? 'Yes' : 'No'}</p>
            </div>
          </div>
        </div>

        {/* Donation History */}
        <div className="bg-white rounded-lg shadow-sm p-6">
          <h2 className="text-xl font-bold mb-4">Donation History</h2>
          {loading ? (
            <div className="text-center text-gray-500 py-8">Loading...</div>
          ) : history?.bloodDonationHistoryResponses && history.bloodDonationHistoryResponses.length > 0 ? (
            <div className="space-y-4">
              {history.bloodDonationHistoryResponses.map((record, idx) => (
                <div key={idx} className="border rounded-lg p-4">
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
                  {record.certificate && (
                    <a
                      href={record.certificate}
                      target="_blank"
                      rel="noopener noreferrer"
                      className="text-blue-500 text-sm hover:text-blue-600"
                    >
                      View Certificate →
                    </a>
                  )}
                </div>
              ))}
            </div>
          ) : (
            <div className="text-center text-gray-500 py-6">
              No donation history found.
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
