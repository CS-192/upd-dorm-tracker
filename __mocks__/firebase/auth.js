module.exports = {
    getAuth: jest.fn(() => ({
      currentUser: { uid: 'test-user-id' }, // Mock a logged-in user
    })),
  };