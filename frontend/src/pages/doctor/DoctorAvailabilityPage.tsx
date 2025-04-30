import { useState } from 'react';
import { ArrowLeft, Plus, Trash2 } from 'lucide-react';
import { Link } from 'react-router-dom';

interface TimeSlot {
  day: string;
  startTime: string;
  endTime: string;
  type: 'emergency' | 'specialized' | 'offline';
}

export function DoctorAvailabilityPage() {
  const [timeSlots, setTimeSlots] = useState<TimeSlot[]>([
    {
      day: 'monday',
      startTime: '09:00',
      endTime: '17:00',
      type: 'specialized'
    }
  ]);

  const days = [
    'monday',
    'tuesday',
    'wednesday',
    'thursday',
    'friday',
    'saturday',
    'sunday'
  ];

  const addTimeSlot = () => {
    setTimeSlots([
      ...timeSlots,
      {
        day: 'monday',
        startTime: '09:00',
        endTime: '17:00',
        type: 'specialized'
      }
    ]);
  };

  const removeTimeSlot = (index: number) => {
    setTimeSlots(timeSlots.filter((_, i) => i !== index));
  };

  const updateTimeSlot = (index: number, field: keyof TimeSlot, value: string) => {
    const updatedSlots = [...timeSlots];
    updatedSlots[index] = {
      ...updatedSlots[index],
      [field]: value
    };
    setTimeSlots(updatedSlots);
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    // Handle saving availability
    console.log('Saving availability:', timeSlots);
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-3xl mx-auto">
        <div className="flex items-center mb-8">
          <Link to="/doctor/dashboard" className="flex items-center text-gray-600 hover:text-gray-800">
            <ArrowLeft className="w-5 h-5 mr-2" />
            Back to Dashboard
          </Link>
        </div>

        <div className="bg-white rounded-lg shadow-sm p-6">
          <h1 className="text-2xl font-bold mb-8">Set Your Availability</h1>

          <form onSubmit={handleSubmit} className="space-y-6">
            {timeSlots.map((slot, index) => (
              <div key={index} className="border rounded-lg p-4">
                <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      Day
                    </label>
                    <select
                      className="w-full p-2 border rounded-lg"
                      value={slot.day}
                      onChange={(e) => updateTimeSlot(index, 'day', e.target.value)}
                    >
                      {days.map((day) => (
                        <option key={day} value={day}>
                          {day.charAt(0).toUpperCase() + day.slice(1)}
                        </option>
                      ))}
                    </select>
                  </div>

                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      Start Time
                    </label>
                    <input
                      type="time"
                      className="w-full p-2 border rounded-lg"
                      value={slot.startTime}
                      onChange={(e) => updateTimeSlot(index, 'startTime', e.target.value)}
                    />
                  </div>

                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      End Time
                    </label>
                    <input
                      type="time"
                      className="w-full p-2 border rounded-lg"
                      value={slot.endTime}
                      onChange={(e) => updateTimeSlot(index, 'endTime', e.target.value)}
                    />
                  </div>

                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      Type
                    </label>
                    <select
                      className="w-full p-2 border rounded-lg"
                      value={slot.type}
                      onChange={(e) => updateTimeSlot(index, 'type', e.target.value as TimeSlot['type'])}
                    >
                      <option value="emergency">Emergency</option>
                      <option value="specialized">Specialized</option>
                      <option value="offline">Offline</option>
                    </select>
                  </div>
                </div>

                <button
                  type="button"
                  className="mt-4 text-red-500 hover:text-red-600"
                  onClick={() => removeTimeSlot(index)}
                >
                  <Trash2 size={20} />
                </button>
              </div>
            ))}

            <button
              type="button"
              className="flex items-center gap-2 text-blue-500 hover:text-blue-600"
              onClick={addTimeSlot}
            >
              <Plus size={20} />
              Add Time Slot
            </button>

            <button
              type="submit"
              className="w-full py-3 bg-blue-500 text-white rounded-full hover:bg-blue-600 transition-colors"
            >
              Save Availability
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}