module.exports = {
    collection: jest.fn(),
    addDoc: jest.fn(() => Promise.resolve()),
  };