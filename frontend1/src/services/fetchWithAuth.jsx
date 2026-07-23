const fetchWithAuth = async (url, options = {}, isRetry = false) => {
  const defaultOptions = {
    credentials: 'include',
    ...options,
  };

  try {
    const response = await fetch(url, defaultOptions);
    if (response.status === 403 && !isRetry) {
      const refreshResponse = await fetch('/api/refresh-token', {
        method: 'POST',
        credentials: 'include',
      });

      if (refreshResponse.ok) {
        return fetch(url, defaultOptions, true);
      } else {
        throw new Error('Unable to refresh token');
      }
    }

     

    return response;
  } catch (error) {
    console.error('Fetch error:', error);
    throw error;
  }
};

export default fetchWithAuth;
