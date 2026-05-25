import { defineConfig } from 'vitepress'

export default defineConfig({
  title: 'Dev Archive',
  description: 'API Documentation & Architecture Decisions',
  themeConfig: {
    nav: [
      { text: 'API Reference', link: '/api/' },
      { text: 'Architecture', link: '/decisions/' },
      { text: 'Frontend Guide', link: '/shared/' }
    ],
    sidebar: {
      '/api/': [
        {
          text: 'Auth API',
          collapsed: false,
          items: [
            { text: '로그인 성공', link: '/api/auth/login-success' },
            { text: '로그인 실패 (자격증명)', link: '/api/auth/login-fail-credentials' },
            { text: '회원가입 성공', link: '/api/auth/signup-success' },
            { text: '회원가입 실패 (중복)', link: '/api/auth/signup-fail-duplicate' }
          ]
        },
        {
          text: 'Board API',
          collapsed: false,
          items: [
            { text: '게시글 생성 성공', link: '/api/board/create-success' },
            { text: '게시글 생성 실패 (미인증)', link: '/api/board/create-fail-unauthorized' }
          ]
        },
        {
          text: 'Member API',
          collapsed: false,
          items: [
            { text: '내 정보 조회', link: '/api/member/get-my-info' }
          ]
        }
      ],
      '/decisions/': [
        {
          text: 'Architecture Decisions',
          items: [
            { text: 'ADR 템플릿', link: '/decisions/ADR-000-template' }
          ]
        }
      ],
      '/shared/': [
        {
          text: 'Frontend Guide',
          items: [
            { text: '시작하기', link: '/shared/' },
            { text: '에러 코드', link: '/shared/error-codes' }
          ]
        }
      ]
    },
    search: {
      provider: 'local'
    },
    socialLinks: [
      { icon: 'github', link: 'https://github.com' }
    ]
  },
  appearance: 'dark'
})
