import { Link } from 'react-router-dom';
import { Droplet, Users, FileText, Award } from 'lucide-react';

export function BloodDonationSection() {
  const stats = [
    { label: 'Blood Donner', value: '1k+', icon: <Droplet className="text-red-500" /> },
    { label: 'Blood Received', value: '5k+', icon: <Users className="text-blue-500" /> },
    { label: 'Post Everyday', value: '100', icon: <FileText className="text-green-500" /> }
  ];

  const recentPosts = [
    {
      id: 1,
      title: 'Need A+ blood in Mirpur 10',
      image: 'https://images.unsplash.com/photo-1615461066841-6116e61058f4?auto=format&fit=crop&w=300&h=200&q=80',
      urgency: 'Urgent'
    },
    {
      id: 2,
      title: 'Emergency blood need!',
      image: 'https://images.unsplash.com/photo-1579165466741-7f35e4755660?auto=format&fit=crop&w=300&h=200&q=80',
      urgency: 'Emergency'
    },
    {
      id: 3,
      title: 'Who can give blood - WHO',
      image: 'https://images.unsplash.com/photo-1536856136534-bb679c52a9aa?auto=format&fit=crop&w=300&h=200&q=80',
      urgency: 'Information'
    }
  ];

  return (
    <div className="py-16 px-6 md:px-12 bg-gradient-to-b from-red-50 to-white">
      <div className="max-w-6xl mx-auto">
        <div className="text-center mb-12">
          <h2 className="text-3xl font-bold mb-4">Blood Donation Network</h2>
          <p className="text-gray-600 max-w-2xl mx-auto">
            Join our blood donation community to help save lives. Whether you need blood or want to donate, 
            we connect donors with recipients in real-time.
          </p>
        </div>

        <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-12">
          <Link to="/blood-donation/donor" className="bg-white p-6 rounded-xl shadow-sm hover:shadow-md transition-all text-center">
            <div className="bg-red-100 w-12 h-12 rounded-full flex items-center justify-center mx-auto mb-4">
              <Droplet className="text-red-500" />
            </div>
            <h3 className="font-semibold">Blood Donor</h3>
          </Link>
          
          <Link to="/blood-donation/recipient" className="bg-white p-6 rounded-xl shadow-sm hover:shadow-md transition-all text-center">
            <div className="bg-blue-100 w-12 h-12 rounded-full flex items-center justify-center mx-auto mb-4">
              <Users className="text-blue-500" />
            </div>
            <h3 className="font-semibold">Blood Recipient</h3>
          </Link>
          
          <Link to="/blood-donation/create-post" className="bg-white p-6 rounded-xl shadow-sm hover:shadow-md transition-all text-center">
            <div className="bg-green-100 w-12 h-12 rounded-full flex items-center justify-center mx-auto mb-4">
              <FileText className="text-green-500" />
            </div>
            <h3 className="font-semibold">Create Post</h3>
          </Link>
          
          <Link to="/blood-donation/history" className="bg-white p-6 rounded-xl shadow-sm hover:shadow-md transition-all text-center">
            <div className="bg-purple-100 w-12 h-12 rounded-full flex items-center justify-center mx-auto mb-4">
              <Award className="text-purple-500" />
            </div>
            <h3 className="font-semibold">Blood Given</h3>
          </Link>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-8 mb-12">
          <div>
            <h3 className="text-xl font-semibold mb-6">Our Contribution</h3>
            <div className="grid grid-cols-3 gap-4">
              {stats.map((stat, index) => (
                <div key={index} className="bg-white p-4 rounded-xl shadow-sm text-center">
                  <div className="flex justify-center mb-2">{stat.icon}</div>
                  <div className="text-2xl font-bold mb-1">{stat.value}</div>
                  <div className="text-sm text-gray-600">{stat.label}</div>
                </div>
              ))}
            </div>
          </div>

          <div>
            <h3 className="text-xl font-semibold mb-6">Recent Posts</h3>
            <div className="space-y-4">
              {recentPosts.map((post) => (
                <Link 
                  key={post.id}
                  to={`/blood-donation/post/${post.id}`}
                  className="flex gap-4 bg-white p-3 rounded-lg shadow-sm hover:shadow-md transition-all"
                >
                  <img
                    src={post.image}
                    alt={post.title}
                    className="w-24 h-24 rounded-lg object-cover"
                  />
                  <div>
                    <h4 className="font-semibold mb-2">{post.title}</h4>
                    <span className={`px-3 py-1 rounded-full text-sm ${
                      post.urgency === 'Urgent' ? 'bg-red-100 text-red-600' :
                      post.urgency === 'Emergency' ? 'bg-orange-100 text-orange-600' :
                      'bg-blue-100 text-blue-600'
                    }`}>
                      {post.urgency}
                    </span>
                  </div>
                </Link>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}