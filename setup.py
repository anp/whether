__author__ = 'adam'

from setuptools import find_packages, setup

setup(name="whether",
      version="0.1",
      description="GSOD -> PostgreSQL utility",
      author="Adam Perry",
      author_email='adam.n.perry@gmail.com',
      platforms=["any"],
      license="MIT",
      url="http://github.com/dikaiosune/whether",
      packages=find_packages(),
      install_requires=[
          "pandas>=0.16.2",
          "psycopg2>=2.6"
      ],
      )
