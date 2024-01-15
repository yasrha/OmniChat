const ApiConfig = {
    baseUrl: process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080',
    endpoints: {
      login: '/api/userAuth/login',
      register: '/api/userAuth/register',
    }
  };
  
  export default ApiConfig;