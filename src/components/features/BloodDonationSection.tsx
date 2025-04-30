import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Droplet, Users, Award } from 'lucide-react';
import { api } from '../../lib/api'; // ✅ Correct path

interface BloodPost {
  id: number;
  patientName: string;
  description: string;
}

interface DashboardData {
  totalDonor: number;
  totalDonation: number;
  getTotalPost: number;
  bloodRequestPosts: BloodPost[];
}

export function BloodDonationSection() {
  const [dashboardData, setDashboardData] = useState<DashboardData | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchDashboard();
  }, []);

  const fetchDashboard = async () => {
    try {
      const { data: baseResponse } = await api.get('/api/v1/blood/dashboard-blood');
      if (baseResponse.code === 'XS0001') {
        setDashboardData(baseResponse.data);
      }
    } catch (error) {
      console.error('Error fetching dashboard:', error);
    } finally {
      setLoading(false);
    }
  };

  const stats = [
    { label: 'Blood Donor', value: dashboardData?.totalDonor || 0, icon: <Droplet className="text-red-500" /> },
    { label: 'Blood Received', value: dashboardData?.totalDonation || 0, icon: <Users className="text-blue-500" /> },
    { label: 'Post Everyday', value: dashboardData?.getTotalPost || 0, icon: <Award className="text-green-500" /> }
  ];

  return (
    <div className="py-16 px-6 md:px-12 bg-gradient-to-b from-red-50 to-white">
      <div className="max-w-6xl mx-auto">
        {/* Header */}
        <div className="text-center mb-12">
          <h2 className="text-3xl font-bold mb-4">Blood Donation Network</h2>
          <p className="text-gray-600 max-w-2xl mx-auto">
            Join our blood donation community to help save lives. Whether you need blood or want to donate, 
            we connect donors with recipients in real-time.
          </p>
        </div>

        {/* Main Links */}
        <div className="grid grid-cols-2 md:grid-cols-4 gap-6 mb-12">
          {[
            { to: "/blood-donation/donor", icon: <Droplet className="text-red-500" />, label: "Blood Donor" },
            { to: "/blood-donation/recipient", icon: <Users className="text-blue-500" />, label: "Blood Recipient" },
            { to: "/blood-donation/history", icon: <Award className="text-purple-500" />, label: "Blood Given" },
            { to: "/blood-donation/donors", icon: <Users className="text-amber-500" />, label: "Find Donors" }
          ].map((item, idx) => (
            <Link
              key={idx}
              to={item.to}
              className="bg-white p-6 rounded-xl shadow-sm hover:shadow-lg transform hover:-translate-y-1 transition-all duration-300 text-center group"
            >
              <div className="bg-red-100 w-12 h-12 rounded-full flex items-center justify-center mx-auto mb-4 group-hover:opacity-80">
                {item.icon}
              </div>
              <h3 className="font-semibold text-gray-800 group-hover:text-red-500 transition-colors duration-300">{item.label}</h3>
            </Link>
          ))}
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
          {/* Our Contribution */}
          <div>
            <h3 className="text-xl font-semibold mb-6">Our Contribution</h3>
            <div className="grid grid-cols-3 gap-4">
              {loading ? (
                <div className="col-span-3 text-center text-gray-500">Loading...</div>
              ) : (
                stats.map((stat, index) => (
                  <div
                    key={index}
                    className="bg-white p-6 rounded-xl shadow-sm text-center hover:shadow-md transform hover:scale-105 transition-all duration-300"
                  >
                    <div className="flex justify-center mb-2">
                      <div className="opacity-90 group-hover:opacity-100 transition-all">{stat.icon}</div>
                    </div>
                    <div className="text-2xl font-bold mb-1">{stat.value}+</div>
                    <div className="text-sm text-gray-600">{stat.label}</div>
                  </div>
                ))
              )}
            </div>
          </div>

          {/* Recent Posts */}
          <div>
            <h3 className="text-xl font-semibold mb-6">Recent Posts</h3>
            <div className="space-y-4">
              {loading ? (
                <div className="text-gray-500">Loading posts...</div>
              ) : dashboardData?.bloodRequestPosts?.length ? (
                dashboardData.bloodRequestPosts.slice(0, 5).map((post) => (
                  <Link 
                    key={post.id}
                    to={`/blood-donation/post/${post.id}`}
                    className="flex gap-4 bg-white p-4 rounded-lg shadow-sm hover:shadow-lg transform hover:scale-105 transition-all duration-300"
                  >
                    <img
                      src="https://images.unsplash.com/photo-1615461066841-6116e61058f4?auto=format&fit=crop&w=300&h=200&q=80"
                      alt={post.patientName}
                      className="w-24 h-24 rounded-lg object-cover transform hover:rotate-1 transition-transform duration-300"
                    />
                    <div>
                      <h4 className="font-semibold mb-1">{post.patientName}</h4>
                      <p className="text-sm text-gray-600 line-clamp-2">{post.description}</p>
                    </div>
                  </Link>
                ))
              ) : (
                <div className="text-gray-500">No recent posts found.</div>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
