import { useState, useRef, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom'; // ✅ Added useNavigate
import { Menu, ChevronDown, User, Search, Bell, Heart, X } from 'lucide-react';
import { Button } from '../ui/Button';
import { useNotificationStore } from '../../stores/notificationStore';
import { logout } from '../../pages/auth/auth'; // ✅ Added logout import

export function Header() {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [isUserMenuOpen, setIsUserMenuOpen] = useState(false);
  const [openSubmenu, setOpenSubmenu] = useState<string | null>(null);
  const [isSearchOpen, setIsSearchOpen] = useState(false);
  const [searchQuery, setSearchQuery] = useState('');
  const menuRef = useRef<HTMLDivElement>(null);
  const userMenuRef = useRef<HTMLDivElement>(null);
  const searchRef = useRef<HTMLDivElement>(null);

  const unreadCount = useNotificationStore(state => state.getUnreadCount());
  const navigate = useNavigate(); // ✅ Setup navigation

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (menuRef.current && !menuRef.current.contains(event.target as Node)) {
        setIsMenuOpen(false);
      }
      if (userMenuRef.current && !userMenuRef.current.contains(event.target as Node)) {
        setIsUserMenuOpen(false);
      }
      if (searchRef.current && !searchRef.current.contains(event.target as Node)) {
        setIsSearchOpen(false);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, []);

  const mainMenuItems = [
    {
      label: 'Find Doctors',
      submenu: [
        { label: 'Book Appointment', link: '/appointment' },
        { label: 'Find Hospitals', link: '/hospitals' },
        { label: 'Emergency Consultation', link: '/appointment/emergency' },
        { label: 'Specialized Doctors', link: '/appointment/specialized' }
      ]
    },
    {
      label: 'Medicine',
      submenu: [
        { label: 'Order Medicine', link: '/medicine' },
        { label: 'Find Pharmacies', link: '/find-pharmacies' }
      ]
    },
    {
      label: 'Blood Donation',
      submenu: [
        { label: 'Blood Donation', link: '/blood-donation' },
        { label: 'Blood Donation Posts', link: '/blood-donation/posts' },
        { label: 'Find Donors', link: '/blood-donation/donors' },
        { label: 'Donate Blood', link: '/blood-donation/donor' },
        { label: 'Request Blood', link: '/blood-donation/recipient' },
        { label: 'Donation History', link: '/blood-donation/history' }
      ]
    },
    {
      label: 'Ambulance',
      link: '/ambulance'
    },
    {
      label: 'Community',
      submenu: [
        { label: 'User Community', link: '/community/users' },
        { label: 'Doctor Articles', link: '/community/doctors' }
      ]
    }
  ];

  const userMenuItems = [
    { label: 'Patient Dashboard', link: '/patient/dashboard' },
    { label: 'Doctor Dashboard', link: '/doctor/dashboard' },
    { label: 'My Appointments', link: '/appointments' }
  ];

  const toggleSubmenu = (label: string) => {
    setOpenSubmenu(openSubmenu === label ? null : label);
  };

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    console.log('Searching for:', searchQuery);
  };

  const handleLogout = () => {
    logout();             // ✅ Clear token and axios
    navigate('/login');   // ✅ Redirect to login
  };

  return (
    <header className="bg-white shadow-sm sticky top-0 z-50">
      <div className="container mx-auto px-4">
        <div className="flex items-center justify-between h-16">
          {/* Logo */}
          <Link to="/" className="flex items-center gap-2">
            <Heart className="w-8 h-8 text-blue-500" />
            <span className="text-2xl font-bold bg-gradient-to-r from-blue-600 to-blue-400 bg-clip-text text-transparent">
              XENON
            </span>
          </Link>

          {/* Desktop Navigation */}
          <nav className="hidden lg:flex items-center space-x-1">
            {mainMenuItems.map((item, index) => (
              item.submenu ? (
                <div key={index} className="relative group">
                  <button className="px-3 py-2 text-gray-700 hover:text-blue-600 rounded-lg group-hover:bg-gray-50 transition-colors flex items-center gap-1">
                    {item.label}
                    <ChevronDown size={16} className="group-hover:transform group-hover:rotate-180 transition-transform" />
                  </button>
                  <div className="absolute left-0 mt-1 w-56 bg-white rounded-xl shadow-lg border border-gray-100 opacity-0 invisible group-hover:opacity-100 group-hover:visible transition-all duration-200 transform translate-y-2 group-hover:translate-y-0">
                    <div className="p-2">
                      {item.submenu.map((subItem, subIndex) => (
                        <Link
                          key={subIndex}
                          to={subItem.link}
                          className="block px-4 py-2 text-gray-700 hover:bg-blue-50 hover:text-blue-600 rounded-lg transition-colors"
                        >
                          {subItem.label}
                        </Link>
                      ))}
                    </div>
                  </div>
                </div>
              ) : (
                <Link
                  key={index}
                  to={item.link}
                  className="px-3 py-2 text-gray-700 hover:text-blue-600 hover:bg-gray-50 rounded-lg transition-colors"
                >
                  {item.label}
                </Link>
              )
            ))}
          </nav>

          {/* Right Section */}
          <div className="hidden lg:flex items-center gap-4">
            <button 
              className="p-2 text-gray-500 hover:text-blue-600 hover:bg-gray-50 rounded-full transition-colors"
              onClick={() => setIsSearchOpen(!isSearchOpen)}
            >
              <Search size={20} />
            </button>
            <Link 
              to="/notifications"
              className="relative p-2 text-gray-500 hover:text-blue-600 hover:bg-gray-50 rounded-full transition-colors"
            >
              <Bell size={20} />
              {unreadCount > 0 && (
                <span className="absolute top-0 right-0 w-5 h-5 bg-red-500 text-white text-xs flex items-center justify-center rounded-full">
                  {unreadCount}
                </span>
              )}
            </Link>

            {/* User Menu */}
            <div className="relative" ref={userMenuRef}>
              <button
                className="flex items-center gap-2 px-3 py-2 text-gray-700 hover:text-blue-600 hover:bg-gray-50 rounded-lg transition-colors"
                onClick={() => setIsUserMenuOpen(!isUserMenuOpen)}
              >
                <User size={20} />
                <ChevronDown size={16} className={`transition-transform ${isUserMenuOpen ? 'rotate-180' : ''}`} />
              </button>
              {isUserMenuOpen && (
                <div className="absolute right-0 mt-2 w-56 bg-white rounded-xl shadow-lg border border-gray-100">
                  <div className="p-2">
                    {userMenuItems.map((item, index) => (
                      <Link
                        key={index}
                        to={item.link}
                        className="block px-4 py-2 text-gray-700 hover:bg-blue-50 hover:text-blue-600 rounded-lg transition-colors"
                        onClick={() => setIsUserMenuOpen(false)}
                      >
                        {item.label}
                      </Link>
                    ))}
                    <hr className="my-2" />
                    <button 
                      className="w-full text-left px-4 py-2 text-red-600 hover:bg-red-50 rounded-lg transition-colors"
                      onClick={() => {
                        setIsUserMenuOpen(false);
                        handleLogout(); // ✅ Desktop Logout
                      }}
                    >
                      Logout
                    </button>
                  </div>
                </div>
              )}
            </div>

            <Link to="/login">
              <Button>Get Started</Button>
            </Link>
          </div>

          {/* Mobile Menu Button */}
          <div className="lg:hidden flex items-center gap-4">
            <button 
              className="p-2 text-gray-500 hover:text-blue-600 hover:bg-gray-50 rounded-full transition-colors"
              onClick={() => setIsSearchOpen(!isSearchOpen)}
            >
              <Search size={20} />
            </button>
            <Link 
              to="/notifications"
              className="relative p-2 text-gray-500 hover:text-blue-600 hover:bg-gray-50 rounded-full transition-colors"
            >
              <Bell size={20} />
              {unreadCount > 0 && (
                <span className="absolute top-0 right-0 w-5 h-5 bg-red-500 text-white text-xs flex items-center justify-center rounded-full">
                  {unreadCount}
                </span>
              )}
            </Link>
            <button 
              className="p-2 text-gray-500 hover:text-blue-600 hover:bg-gray-50 rounded-lg transition-colors"
              onClick={() => setIsMenuOpen(!isMenuOpen)}
            >
              {isMenuOpen ? <X size={24} /> : <Menu size={24} />}
            </button>
          </div>
        </div>
      </div>

      {/* Search, Mobile Menu... (not modified) */}
    </header>
  );
}
