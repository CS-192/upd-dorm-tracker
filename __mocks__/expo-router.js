module.exports = {
  useRouter: jest.fn(() => ({
    back: jest.fn(),
  })),
};