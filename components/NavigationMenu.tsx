import React, { useRef, useEffect } from 'react';
import {
  Animated,
  TouchableOpacity,
  StyleSheet,
  View,
  Text,
} from 'react-native';
import { useRouter } from 'expo-router';

type MenuItem = {
  label: string;
  route: string;
};

interface NavigationMenuProps {
  isVisible: boolean;
  toggleMenu: () => void;
  userRole: 'admin' | 'student';
}

const HEADER_HEIGHT = 54; // adjust as needed

const NavigationMenu: React.FC<NavigationMenuProps> = ({
  isVisible,
  toggleMenu,
  userRole,
}) => {
  const router = useRouter();
  const slideAnim = useRef(new Animated.Value(0)).current;

  useEffect(() => {
    Animated.timing(slideAnim, {
      toValue: isVisible ? 1 : 0,
      duration: 300,
      useNativeDriver: true,
    }).start();
  }, [isVisible, slideAnim]);

  const translateX = slideAnim.interpolate({
    inputRange: [0, 1],
    outputRange: [-420, 0], // adjust to your menu width
  });

  const backdropOpacity = slideAnim.interpolate({
    inputRange: [0, 1],
    outputRange: [0, 0.5],
  });

  // Convert menu items into objects with label and route.
  const adminMenuItems: MenuItem[] = [
    { label: 'Dashboard', route: '/dashboard' },
    { label: 'Scan UP ID', route: '/scan-up-id' },
    { label: "Maintain Dormer's Information", route: '/manage-dormers' },
    { label: 'Maintain Requests', route: '/manage-requests' },
    { label: 'View & Update Dorm Details', route: '/manage-dorm-details' },
  ];
  
  const studentMenuItems: MenuItem[] = [
    { label: 'Dashboard', route: '/dashboard' },
    { label: 'Announcements', route: '/announcements' },
    { label: 'Create a Request', route: '/create-request' },
    { label: 'Late Night/Overnight Pass', route: '/create-request/late-pass' },
    { label: 'Monthly Bill', route: '/create-request/monthly-bill' },
    { label: 'Report/Concerns', route: '/create-request/report' },
    { label: 'View Dorm Details', route: '/manage-dorm-details' },
    { label: 'FAQs', route: '/faqs' },
  ];  

  const menuItems = userRole === 'admin' ? adminMenuItems : studentMenuItems;

  return (
    <View style={[styles.rootContainer, { top: HEADER_HEIGHT }]} pointerEvents={isVisible ? 'auto' : 'none'}>
      {isVisible && (
        <Animated.View style={[styles.backdrop, { opacity: backdropOpacity }]}>
          <TouchableOpacity style={StyleSheet.absoluteFill} onPress={toggleMenu} />
        </Animated.View>
      )}

      <Animated.View style={[styles.menuContainer, { transform: [{ translateX }] }]}>
        {menuItems.map((item, index) => (
          <Text
            key={index}
            style={styles.menuItem}
            onPress={() => {
              toggleMenu(); // close the menu first
              router.push(item.route); // navigate to the selected route
            }}
          >
            {item.label}
          </Text>
        ))}
      </Animated.View>
    </View>
  );
};

export default NavigationMenu;

const styles = StyleSheet.create({
  rootContainer: {
    position: 'absolute',
    left: 0,
    right: 0,
    bottom: 0,
    zIndex: 999,
  },
  backdrop: {
    ...StyleSheet.absoluteFillObject,
    backgroundColor: '#000',
  },
  menuContainer: {
    position: 'absolute',
    top: 0,
    bottom: 0,
    width: 420, // adjust this to your desired menu width
    backgroundColor: '#333',
  },
  menuItem: {
    color: '#fff',
    fontSize: 18,
    padding: 16,
    borderBottomWidth: 1,
    borderBottomColor: '#444',
  },
});
