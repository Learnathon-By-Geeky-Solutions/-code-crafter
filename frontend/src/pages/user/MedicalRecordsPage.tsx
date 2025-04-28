import { useState } from 'react';
import { FileText, Download, Upload, Filter, Search, Plus, X } from 'lucide-react';

interface MedicalRecord {
  id: string;
  title: string;
  type: string;
  date: string;
  doctor: string;
  hospital: string;
  file: string;
  size: string;
}

export function MedicalRecordsPage() {
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedType, setSelectedType] = useState('all');
  const [showUploadModal, setShowUploadModal] = useState(false);
  const [uploadFile, setUploadFile] = useState<File | null>(null);

  const records: MedicalRecord[] = [
    {
      id: '1',
      title: 'Annual Health Checkup Report',
      type: 'Lab Report',
      date: '2024-03-15',
      doctor: 'Dr. Sarah Johnson',
      hospital: 'City General Hospital',
      file: '/reports/checkup.pdf',
      size: '2.5 MB'
    },
    {
      id: '2',
      title: 'Chest X-Ray',
      type: 'Radiology',
      date: '2024-02-28',
      doctor: 'Dr. Michael Chen',
      hospital: 'Metropolitan Hospital',
      file: '/reports/xray.pdf',
      size: '5.1 MB'
    },
    {
      id: '3',
      title: 'Blood Test Results',
      type: 'Lab Report',
      date: '2024-02-15',
      doctor: 'Dr. Emily Rodriguez',
      hospital: 'City General Hospital',
      file: '/reports/blood-test.pdf',
      size: '1.8 MB'
    }
  ];

  const recordTypes = ['Lab Report', 'Radiology', 'Prescription', 'Vaccination', 'Surgery'];

  const filteredRecords = records.filter(record => {
    const matchesSearch = 
      record.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
      record.doctor.toLowerCase().includes(searchTerm.toLowerCase()) ||
      record.hospital.toLowerCase().includes(searchTerm.toLowerCase());
    
    const matchesType = selectedType === 'all' || record.type === selectedType;

    return matchesSearch && matchesType;
  });

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-4xl mx-auto">
        <div className="flex items-center justify-between mb-8">
          <h1 className="text-2xl font-bold">Medical Records</h1>
          <button
            onClick={() => setShowUploadModal(true)}
            className="flex items-center gap-2 px-6 py-2 bg-blue-500 text-white rounded-full hover:bg-blue-600 transition-colors"
          >
            <Upload size={20} />
            Upload Record
          </button>
        </div>

        {/* Search and Filter */}
        <div className="bg-white rounded-lg shadow-sm p-6 mb-8">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div className="relative">
              <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
              <input
                type="text"
                placeholder="Search records..."
                className="w-full pl-10 pr-4 py-2 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </div>
            <div className="flex items-center gap-2">
              <Filter size={20} className="text-gray-400" />
              <select
                className="flex-grow p-2 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                value={selectedType}
                onChange={(e) => setSelectedType(e.target.value)}
              >
                <option value="all">All Types</option>
                {recordTypes.map(type => (
                  <option key={type} value={type}>{type}</option>
                ))}
              </select>
            </div>
          </div>
        </div>

        {/* Records List */}
        <div className="space-y-4">
          {filteredRecords.length === 0 ? (
            <div className="text-center py-12 bg-gray-50 rounded-lg">
              <p className="text-gray-500">No records found</p>
            </div>
          ) : (
            filteredRecords.map((record) => (
              <div key={record.id} className="bg-white rounded-lg shadow-sm p-6">
                <div className="flex items-start gap-4">
                  <div className="p-3 bg-blue-50 rounded-lg">
                    <FileText className="text-blue-500" size={24} />
                  </div>
                  <div className="flex-grow">
                    <div className="flex items-center justify-between mb-2">
                      <h3 className="font-semibold">{record.title}</h3>
                      <span className="px-3 py-1 bg-blue-50 text-blue-600 rounded-full text-sm">
                        {record.type}
                      </span>
                    </div>
                    <div className="grid grid-cols-2 gap-4 text-sm text-gray-600 mb-4">
                      <div>
                        <p className="font-medium">Doctor</p>
                        <p>{record.doctor}</p>
                      </div>
                      <div>
                        <p className="font-medium">Hospital</p>
                        <p>{record.hospital}</p>
                      </div>
                      <div>
                        <p className="font-medium">Date</p>
                        <p>{new Date(record.date).toLocaleDateString()}</p>
                      </div>
                      <div>
                        <p className="font-medium">File Size</p>
                        <p>{record.size}</p>
                      </div>
                    </div>
                    <div className="flex justify-end">
                      <a
                        href={record.file}
                        download
                        className="flex items-center gap-2 text-blue-500 hover:text-blue-600"
                      >
                        <Download size={20} />
                        Download
                      </a>
                    </div>
                  </div>
                </div>
              </div>
            ))
          )}
        </div>
      </div>

      {/* Upload Modal */}
      {showUploadModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4">
          <div className="bg-white rounded-lg p-6 max-w-md w-full">
            <div className="flex items-center justify-between mb-6">
              <h3 className="text-lg font-semibold">Upload Medical Record</h3>
              <button
                onClick={() => setShowUploadModal(false)}
                className="text-gray-400 hover:text-gray-600"
              >
                <X size={24} />
              </button>
            </div>

            <form className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Record Title
                </label>
                <input
                  type="text"
                  className="w-full p-2 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                  placeholder="Enter record title"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Record Type
                </label>
                <select className="w-full p-2 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200">
                  <option value="">Select type</option>
                  {recordTypes.map(type => (
                    <option key={type} value={type}>{type}</option>
                  ))}
                </select>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Date
                </label>
                <input
                  type="date"
                  className="w-full p-2 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Upload File
                </label>
                <label className="block w-full p-4 border-2 border-dashed border-gray-300 rounded-lg cursor-pointer hover:border-blue-300 transition-colors">
                  <div className="flex flex-col items-center">
                    <Plus size={24} className="text-gray-400 mb-2" />
                    <p className="text-sm text-gray-500">Click to upload or drag and drop</p>
                    <p className="text-xs text-gray-400">PDF, JPG, PNG (max. 10MB)</p>
                  </div>
                  <input
                    type="file"
                    className="hidden"
                    onChange={(e) => setUploadFile(e.target.files?.[0] || null)}
                  />
                </label>
                {uploadFile && (
                  <p className="mt-2 text-sm text-green-600">
                    Selected file: {uploadFile.name}
                  </p>
                )}
              </div>

              <div className="flex justify-end gap-4 pt-4">
                <button
                  type="button"
                  onClick={() => setShowUploadModal(false)}
                  className="px-4 py-2 text-gray-600 hover:text-gray-800"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="px-6 py-2 bg-blue-500 text-white rounded-full hover:bg-blue-600"
                >
                  Upload Record
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}