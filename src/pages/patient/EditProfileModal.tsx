

import { X } from 'lucide-react';

interface EditProfileModalProps {
  editProfile: {
    firstName: string;
    lastName: string;
    email: string;
    gender: string;
    area: string;
  };
  setEditProfile: (profile: any) => void;
  onClose: () => void;
  onSubmit: (e: React.FormEvent) => void;
}

export function EditProfileModal({ editProfile, setEditProfile, onClose, onSubmit }: EditProfileModalProps) {
  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
      <div className="bg-white rounded-lg p-6 max-w-2xl w-full max-h-[90vh] overflow-y-auto">
        <div className="flex items-center justify-between mb-6">
          <h2 className="text-xl font-semibold">Edit Profile</h2>
          <button onClick={onClose} className="text-gray-500 hover:text-gray-700">
            <X size={24} />
          </button>
        </div>

        <form onSubmit={onSubmit} className="space-y-6">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">

            {/* First Name */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">First Name</label>
              <input
                type="text"
                value={editProfile.firstName}
                onChange={(e) => setEditProfile({ ...editProfile, firstName: e.target.value })}
                className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
              />
            </div>

            {/* Last Name */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">Last Name</label>
              <input
                type="text"
                value={editProfile.lastName}
                onChange={(e) => setEditProfile({ ...editProfile, lastName: e.target.value })}
                className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
              />
            </div>

            {/* Email */}
            <div className="md:col-span-2">
              <label className="block text-sm font-medium text-gray-700 mb-2">Email</label>
              <input
                type="email"
                value={editProfile.email}
                onChange={(e) => setEditProfile({ ...editProfile, email: e.target.value })}
                className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
              />
            </div>

            {/* Gender */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">Gender</label>
              <select
                value={editProfile.gender}
                onChange={(e) => setEditProfile({ ...editProfile, gender: e.target.value })}
                className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
              >
                <option value="MALE">Male</option>
                <option value="FEMALE">Female</option>
                <option value="OTHER">Other</option>
              </select>
            </div>

            {/* Area */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">Area</label>
              <input
                type="text"
                value={editProfile.area}
                onChange={(e) => setEditProfile({ ...editProfile, area: e.target.value })}
                className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
              />
            </div>

          </div>

          <div className="flex justify-end gap-4 mt-6">
            <button
              type="button"
              className="px-6 py-2 text-gray-600 hover:text-gray-800"
              onClick={onClose}
            >
              Cancel
            </button>
            <button
              type="submit"
              className="px-6 py-2 bg-blue-500 text-white rounded-full hover:bg-blue-600"
            >
              Save Changes
            </button>
          </div>

        </form>
      </div>
    </div>
  );
}
