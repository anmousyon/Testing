using System;
using System.Collections.Generic;
using RedditSharp;
using Newtonsoft.Json;
using System.IO;
using Newtonsoft.Json.Bson;

namespace ellie
{
	class MainClass
	{
		public static void Main(string[] args)
		{
			Console.WriteLine("Hello World!");
		}
	}

	public class Controller
	{
		public Database database;

		public Controller()
		{
			this.database = new Database();
		}

		public void displayDatabase()
		{
			Cleaner cleaner = new Cleaner();

			var posts = database.posts.FindSync(new MongoDB.Bson.BsonDocument());

			foreach (MongoDB.Bson.BsonDocument postData in posts)
			{
				Console.WriteLine(postData);
			}
		}

		public void buildDatabase()
		{
			Reddit reddit = new Reddit();
			Cleaner cleaner = new Cleaner();
			foreach (var subreddit in reddit.getSubreddits())
			{
				foreach (var post in reddit.getPosts(subreddit))
				{
					this.database.insert(cleaner.clean(post), new Post();
					foreach (var comment in reddit.getComments(post))
					{
						this.database.insert(cleaner.clean(comment), new Comment());
					}
				}
			}
		}
	}

	public class Database
	{
		public MongoDB.Driver.MongoClient client;
		public MongoDB.Driver.IMongoCollection<MongoDB.Bson.BsonDocument> posts, comments;
		public Database()
		{
			this.client = new MongoDB.Driver.MongoClient();
			Tuple < MongoDB.Driver.IMongoCollection<MongoDB.Bson.BsonDocument>, MongoDB.Driver.IMongoCollection<MongoDB.Bson.BsonDocument>> collections = load();
			this.posts = collections.Item1;
			this.comments = collections.Item2;
		}

		public Tuple<MongoDB.Driver.IMongoCollection<MongoDB.Bson.BsonDocument>, MongoDB.Driver.IMongoCollection<MongoDB.Bson.BsonDocument>> load()
		{
			MongoDB.Driver.IMongoDatabase database = client.GetDatabase("submissions");
			return Tuple.Create(
				database.GetCollection<MongoDB.Bson.BsonDocument>("Post"),
				database.GetCollection<MongoDB.Bson.BsonDocument>("Comment")
			);
		}

		public void insert(MongoDB.Bson.BsonDocument post)
		{
			posts.InsertOne(post);	
		}

		public void insert(MongoDB.Bson.BsonDocument comment)
		{
			posts.InsertOne(comment);
		}
	}

	public class Cleaner
	{
		public Analyzer analyzer;
		public Cleaner()
		{
			this.analyzer = new Analyzer();
		}

		public MongoDB.Bson.BsonDocument clean(RedditSharp.Things.Post post)
		{
			Post dirty = new Post(post);
			dirty.TitleSentiment = analyzer.calcSentiment(post.Title);
			return dump(dirty);
		}

		public MongoDB.Bson.BsonDocument clean(RedditSharp.Things.Comment comment)
		{
			Comment dirty = new Comment(comment);
			dirty.BodySentiment = analyzer.calcSentiment(comment.Body);
			return dump(dirty);
		}

		public MongoDB.Bson.BsonDocument dump(Post post)
		{
			var document = new MongoDB.Bson.BsonDocument
			{
				{ "label", post.label },
				{ "subreddit", post.subreddit },
				{ "username", post.username },
				{ "score", post.score },
				{ "titleSentiment", post.TitleSentiment }
			};
			return document;
		}

		public MongoDB.Bson.BsonDocument dump(Comment comment)
		{
			var document = new MongoDB.Bson.BsonDocument
			{
				{ "label", comment.label },
				{ "subreddit", comment.subreddit },
				{ "username", comment.username },
				{ "score", comment.score },
				{ "bodySentiment", comment.BodySentiment }
			};
			return document;
		}

		public Post load(MongoDB.Bson.BsonDocument post, Post loaded)
		{
			loaded = new Post(
				post["label"].ToString(),
				post["subreddit"].ToString(),
				post["username"].ToString(),
				post["score"].ToString(),
				post["bodySentiment"].ToString()
			);
			return loaded;
		}

		public Comment load(string comment, Comment loaded)
		{
			byte[] data = Convert.FromBase64String(comment);
			MemoryStream ms = new MemoryStream(data);
			using (BsonReader reader = new BsonReader(ms))
			{
				JsonSerializer serializer = new JsonSerializer();
				loaded = serializer.Deserialize<Comment>(reader);
				return loaded;
			}
		}
	}

	public class Reddit
	{
		private BotWebAgent webagent;
		public RedditSharp.Reddit reddit;

		public Reddit()
		{
			reddit = new RedditSharp.Reddit(login(), false);
		}

		public BotWebAgent login()
		{
			string[] keys = System.IO.File.ReadAllLines(@"keys.txt");
			var username = keys[0];
			var password = keys[1];
			var id = keys[3];
			var secret = keys[4];
			var uri = keys[5];
			webagent = new BotWebAgent(username, password, id, secret, uri);
			return webagent;
		}

		public List<RedditSharp.Things.Subreddit> getSubreddits()
		{
			string[] subredditNames = System.IO.File.ReadAllLines(@"subreddits.txt");
			List<RedditSharp.Things.Subreddit> subreddits = new List<RedditSharp.Things.Subreddit> { };
			foreach (string name in subredditNames)
			{
				subreddits.Add(reddit.GetSubreddit("/r/" + name));
			}
			return subreddits;
		}

		public Listing<RedditSharp.Things.Post> getPosts(RedditSharp.Things.Subreddit subreddit)
		{
			return subreddit.Posts;
		}

		public RedditSharp.Things.Comment[] getComments(RedditSharp.Things.Post post)
		{
			return post.Comments;
		}
	}

	public class Analyzer
	{
		public Analyzer()
		{
		}

		public string calcSentiment(string text)
		{
			return i4Ds.LanguageToolkit.SentimentAnalyzer.GetSentiment(text).ToString();
		}
	}

	public class Submission
	{
		public string label;
		public string subreddit;
		public string username;
		public string score;

		public Submission(RedditSharp.Things.Post item)
		{
			this.label = item.Id;
			this.subreddit = item.Subreddit.FullName;
			this.username = item.Author.FullName;
			this.score = item.Score.ToString();
		}

		public Submission(
			string label,
			string subreddit,
			string username,
			string score
		)
		{
			this.label = label;
			this.subreddit = subreddit;
			this.username = username;
			this.score = score;
		}

		public Submission(RedditSharp.Things.Comment item)
		{
			this.label = item.Id;
			this.subreddit = item.Subreddit;
			this.username = item.Author;
			this.score = item.Score.ToString();
		}
	}

	public class Post : Submission
	{
		private string titleSentiment;
		public string TitleSentiment
		{
			get
			{
				return titleSentiment;
			}
			set
			{
				titleSentiment = value;
			}
		}
		public Post(
			string label,
			string subreddit,
			string username,
			string score,
			string titleSentiment
		) : base(label, subreddit, username, score)
		{
			this.TitleSentiment = titleSentiment;
		}
		public Post(RedditSharp.Things.Post post) : base(post)
		{
		}
	}

	public class Comment : Submission
	{
		private string bodySentiment;
		public string BodySentiment
		{
			get
			{
				return bodySentiment;
			}
			set
			{
				bodySentiment = value;
			}
		}
		public Comment(
			string label,
			string subreddit,
			string username,
			string score,
			string bodySentiment
		) : base(label, subreddit, username, score)
		{
			this.BodySentiment = bodySentiment;
		}
		public Comment(RedditSharp.Things.Comment comment) : base(comment)
		{
		}
	}
}